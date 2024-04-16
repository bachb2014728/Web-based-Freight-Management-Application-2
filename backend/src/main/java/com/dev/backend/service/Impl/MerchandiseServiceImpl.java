package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.service.MerchandiseService;
import com.dev.backend.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class MerchandiseServiceImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final ImageRepository imageRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Override
    public List<MerchandiseDto> getAllMerchandise() {
        List<Merchandise> merchandises = merchandiseRepository.findAll();
        return merchandises.stream().map(this::mapMerchandiseToMerchandiseDto).collect(Collectors.toList());
    }

    @Override
    public void saveNewMerchandise(CreateMerchandise merchandise, MultipartFile[] photos) throws IOException {
        Information infoSender = Information.builder()
                .name(merchandise.getNameSender())
                .phone(merchandise.getPhoneSender())
                .address(
                        getAddress(merchandise.getProvinceSender(),merchandise.getDistrictSender(), merchandise.getWardSender())
                )
                .build();
        List<Image> images = new ArrayList<>();
        for (MultipartFile photo : photos) {
            Image image = Image.builder()
                    .name(photo.getOriginalFilename())
                    .type(photo.getContentType())
                    .imageData(compressImage(photo.getBytes()))
                    .build();
            Image imageNew = imageRepository.save(image);
            images.add(imageNew);
        }
        Merchandise merchandiseNew = Merchandise.builder()
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .weight(merchandise.getWeight())
                .status("AWAITING")
                .sender(infoSender)
                .receiver(
                        Information.builder()
                                .name(merchandise.getNameReceiver())
                                .phone(merchandise.getPhoneReceiver())
                                .address(
                                        getAddress(merchandise.getProvinceReceiver(), merchandise.getDistrictReceiver(), merchandise.getWardReceiver())
                                )
                                .build()
                )
                .images(images)
                .build();
        merchandiseRepository.save(merchandiseNew);
    }

    @Override
    public MerchandiseDto getOneMerchandise(String id) {
        Merchandise merchandise = merchandiseRepository.findById(id).get();
        return mapMerchandiseToMerchandiseDto(merchandise);
    }

    @Override
    public void deleteOneMerchandise(String id) {
        if (merchandiseRepository.existsById(id)){
            List<Image> images = merchandiseRepository.findById(id).get().getImages();
            for (Image image : images){
                if(imageRepository.existsById(image.getId())){
                    imageRepository.delete(image);
                }
            }
            merchandiseRepository.deleteById(id);
        }
    }

    @Override
    public EditMerchandise getOneMerchandiseEdit(String id) {
        if(merchandiseRepository.existsById(id)){
            Merchandise merchandise = merchandiseRepository.findById(id).get();
            List<byte[]> images = new ArrayList<>();
            for(Image image : merchandise.getImages()){
                Optional<Image> imageItem = imageRepository.findById(image.getId());
                images.add(decompressImage(imageItem.get().getImageData()));
            }
            return EditMerchandise.builder()
                    .name(merchandise.getName())
                    .price(merchandise.getPrice())
                    .weight(merchandise.getWeight())
                    .sender(merchandise.getSender())
                    .images(images)
                    .receiver(merchandise.getReceiver())
                    .build();
        }
        return null;
    }

    @Override
    public void updateMerchandise(EditMerchandise editMerchandise, String id, MultipartFile[] photos) throws IOException {

        Merchandise merchandise = merchandiseRepository.findById(id).get();
        merchandise.setName(editMerchandise.getName());
        merchandise.setPrice(editMerchandise.getPrice());
        merchandise.setWeight(editMerchandise.getWeight());
        merchandise.setSender(
                Information.builder()
                        .name(editMerchandise.getSender().getName())
                        .phone(editMerchandise.getSender().getPhone())
                        .address(
                                getAddress(
                                        editMerchandise.getSender().getAddress().getProvince().getName(),
                                        editMerchandise.getSender().getAddress().getDistrict().getName(),
                                        editMerchandise.getSender().getAddress().getWard().getName()
                                )
                        )
                        .build()
        );
        merchandise.setReceiver(
                Information.builder()
                        .name(editMerchandise.getReceiver().getName())
                        .phone(editMerchandise.getReceiver().getPhone())
                        .address(
                                getAddress(
                                        editMerchandise.getReceiver().getAddress().getProvince().getName(),
                                        editMerchandise.getReceiver().getAddress().getDistrict().getName(),
                                        editMerchandise.getReceiver().getAddress().getWard().getName()
                                )
                        )
                        .build()
        );
        if(photos != null){
            boolean status = false;
            List<Image> imageUpdates = new ArrayList<>();
            for (MultipartFile photo : photos) {
                Image image = Image.builder()
                        .name(photo.getOriginalFilename())
                        .type(photo.getContentType())
                        .imageData(compressImage(photo.getBytes()))
                        .build();
                Image imageNew = imageRepository.save(image);
                if(Objects.equals(imageNew.getType(), "image/png")){
                    status =true;
                    imageUpdates.add(image);
                }else{
                    imageRepository.deleteById(imageNew.getId());
                }
            }
            if (status){
                for (Image imageItem : merchandise.getImages()){
                    imageRepository.deleteById(imageItem.getId());
                }
                merchandise.setImages(imageUpdates);
            }

        }
        merchandiseRepository.save(merchandise);
    }

    public byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public MerchandiseDto mapMerchandiseToMerchandiseDto(Merchandise merchandise){
        List<byte[]> images = new ArrayList<>();
        for(Image image : merchandise.getImages()){
            Optional<Image> imageItem = imageRepository.findById(image.getId());
            images.add(decompressImage(imageItem.get().getImageData()));
        }
        return MerchandiseDto.builder()
                .id(merchandise.getId())
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .images(images)
                .status(merchandise.getStatus())
                .sender(merchandise.getSender())
                .receiver(merchandise.getReceiver())
                .createdAt(merchandise.getCreatedAt())
                .weight(merchandise.getWeight())
                .updatedOn(merchandise.getUpdatedOn())
                .build();
    }
    public  byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }
    @NotNull
    private Address getAddress(String provinceRoot, String districtRoot , String wardRoot) {
        Address address = Address.builder().build();
        if(!provinceRepository.existsByName(provinceRoot)){
            Ward ward = Ward.builder().name(wardRoot).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(districtRoot)
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = Province.builder()
                    .name(provinceRoot)
                    .districts(Collections.singletonList(districtNew))
                    .build();
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else if (!districtRepository.existsByName(districtRoot)){
            Ward ward = Ward.builder().name(wardRoot).build();
            Ward wardNew = wardRepository.save(ward);
            District district = District
                    .builder().name(districtRoot)
                    .wards(Collections.singletonList(wardNew))
                    .build();
            District districtNew  = districtRepository.save(district);
            Province province = provinceRepository.findByName(provinceRoot).get();
            province.getDistricts().add(districtNew);
            Province provinceNew = provinceRepository.save(province);
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(provinceNew);
        }else{
            Ward ward = Ward.builder().name(wardRoot).build();
            Ward wardNew = wardRepository.save(ward);
            District district = districtRepository.findByName(districtRoot).get();
            district.getWards().add(wardNew);
            District districtNew = districtRepository.save(district);
            Province province = provinceRepository.findByName(provinceRoot).get();
            address.setWard(wardNew);
            address.setDistrict(districtNew);
            address.setProvince(province);
        }
        return address;
    }
}
