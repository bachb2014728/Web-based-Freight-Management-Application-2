package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.rest.dto.MerchandiseResponse;
import com.dev.backend.rest.dto.MerchandiseUpdateRequest;
import com.dev.backend.rest.dto.MessageObject;
import com.dev.backend.rest.dto.address.InformationRequest;
import com.dev.backend.service.MerchandiseService;
import com.dev.backend.service.helper.ConvertAddress;
import com.dev.backend.service.helper.ConvertImage;
import com.dev.backend.service.helper.ConvertMerchandise;
import com.dev.backend.service.helper.ConvertUser;
import com.dev.backend.web.dto.*;
import com.dev.backend.web.dto.location.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class MerchandiseServiceImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final ImageRepository imageRepository;
    private final ConvertAddress convertAddress;
    private final ConvertUser convertUser;
    private final ConvertImage convertImage;
    private final ConvertMerchandise convertMerchandise;

    @Override
    public List<MerchandiseDto> getAllMerchandise() {
        List<Merchandise> merchandises = merchandiseRepository.findAll();
        return merchandises.stream().map(convertMerchandise::mapMerchandiseToMerchandiseDto).collect(Collectors.toList());
    }

    @Override
    public void saveNewMerchandise(CreateMerchandise merchandise, MultipartFile[] photos) throws IOException {
        Information infoSender = Information.builder()
                .name(merchandise.getNameSender())
                .phone(merchandise.getPhoneSender())
                .address(
                        convertAddress.getAddress(merchandise.getProvinceSender(),merchandise.getDistrictSender(), merchandise.getWardSender())
                )
                .build();
        List<Image> images = new ArrayList<>();
        for (MultipartFile photo : photos) {
            Image image = Image.builder()
                    .name(photo.getOriginalFilename())
                    .type(photo.getContentType())
                    .imageData(convertImage.compressImage(photo.getBytes()))
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
                                        convertAddress.getAddress(merchandise.getProvinceReceiver(), merchandise.getDistrictReceiver(), merchandise.getWardReceiver())
                                )
                                .build()
                )
                .images(images)
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .build();
        merchandiseRepository.save(merchandiseNew);
    }

    @Override
    public MerchandiseDto getOneMerchandise(String id) {
        Merchandise merchandise = merchandiseRepository.findById(id).orElseThrow();
        return convertMerchandise.mapMerchandiseToMerchandiseDto(merchandise);
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
                images.add(convertImage.decompressImage(imageItem.get().getImageData()));
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
        merchandise.setUpdatedOn(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime());
        merchandise.setSender(
                Information.builder()
                        .name(editMerchandise.getSender().getName())
                        .phone(editMerchandise.getSender().getPhone())
                        .address(
                                convertAddress.getAddress(
                                        ProvinceDto.builder()
                                                .code(editMerchandise.getSender().getAddress().getProvince().getCode())
                                                .name(editMerchandise.getSender().getAddress().getProvince().getName())
                                                .build(),
                                        DistrictDto.builder()
                                                .code(editMerchandise.getSender().getAddress().getDistrict().getId())
                                                .name(editMerchandise.getSender().getAddress().getDistrict().getName())
                                                .build(),
                                        WardDto.builder()
                                                .code(editMerchandise.getSender().getAddress().getWard().getCode())
                                                .name(editMerchandise.getSender().getAddress().getWard().getName())
                                                .build()
                                )
                        )
                        .build()
        );
        merchandise.setReceiver(
                Information.builder()
                        .name(editMerchandise.getReceiver().getName())
                        .phone(editMerchandise.getReceiver().getPhone())
                        .address(
                                convertAddress.getAddress(
                                        ProvinceDto.builder()
                                                .code(editMerchandise.getReceiver().getAddress().getProvince().getCode())
                                                .name(editMerchandise.getReceiver().getAddress().getProvince().getName())
                                                .build(),
                                        DistrictDto.builder()
                                                .code(editMerchandise.getReceiver().getAddress().getDistrict().getId())
                                                .name(editMerchandise.getReceiver().getAddress().getDistrict().getName())
                                                .build(),
                                        WardDto.builder()
                                                .code(editMerchandise.getReceiver().getAddress().getWard().getCode())
                                                .name(editMerchandise.getReceiver().getAddress().getWard().getName())
                                                .build()
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
                        .imageData(convertImage.compressImage(photo.getBytes()))
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

    @Override
    public Object updateMerchandiseOfUser(MerchandiseUpdateRequest merchandise, String id) {
        Merchandise merchandiseOld = merchandiseRepository.findById(id).get();
        merchandiseOld.setName(merchandise.getName());
        merchandiseOld.setPrice(merchandise.getPrice());
        merchandiseOld.setWeight(merchandise.getWeight());
        merchandiseOld.setUpdatedOn(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime());
        if((long) merchandise.getImages().size() > 0){
            List<Image> images = new ArrayList<>();
            for (String imageId : merchandise.getImages()){
                if(imageRepository.existsById(imageId)){
                    images.add(imageRepository.findById(imageId).get());
                }
            }
            for (Image image : merchandiseOld.getImages()){
                imageRepository.deleteById(image.getId());
            }
            merchandiseOld.setImages(images);
        }else{
            merchandiseOld.setImages(merchandiseOld.getImages());
        }
        return convertMerchandise.mapMerchandiseToMerchandiseResponse(merchandiseRepository.save(merchandiseOld));
    }

    @Override
    public Object deleteAllMerchandisesOfUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public List<MerchandiseResponse> getAllMerchandiseOfUser(HttpServletRequest request) {
        UserDocument user = convertUser.getUserFromToken(request);
        List<Merchandise> merchandises = merchandiseRepository.findAllByCode(user);
        return merchandises.stream().map(convertMerchandise::mapMerchandiseToMerchandiseResponse).collect(Collectors.toList());
    }
    @Override
    public Object deleteOneMerchandiseById(String id) {
        if (merchandiseRepository.existsById(id)){
            Merchandise merchandise = merchandiseRepository.findById(id).get();
            for(Image item : merchandise.getImages()){
                imageRepository.delete(item);
            }
            merchandiseRepository.delete(merchandise);
            return MessageObject.builder().message("Xóa thành công").build();
        }
        return null;
    }

    @Override
    public MerchandiseDto saveNewMerchandiseByUser(CreateMerchandise merchandise, HttpServletRequest request) {
        UserDocument user = convertUser.getUserFromToken(request);
        Merchandise merchandiseNew = Merchandise.builder()
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .weight(merchandise.getWeight())
                .status("AWAITING")
                .sender(
                        Information.builder()
                                .name(merchandise.getNameSender())
                                .phone(merchandise.getPhoneSender())
                                .address(
                                        convertAddress.getAddress(merchandise.getProvinceSender(),merchandise.getDistrictSender(), merchandise.getWardSender())
                                )
                                .build()
                )
                .receiver(
                        Information.builder()
                                .name(merchandise.getNameReceiver())
                                .phone(merchandise.getPhoneReceiver())
                                .address(
                                        convertAddress.getAddress(merchandise.getProvinceReceiver(), merchandise.getDistrictReceiver(), merchandise.getWardReceiver())
                                )
                                .build()
                )
                .images(convertUser.convertImageData(merchandise))
                .code(user)
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime())
                .build();
        Merchandise merchandiseObject = merchandiseRepository.save(merchandiseNew);
        return convertMerchandise.mapMerchandiseToMerchandiseDto(merchandiseObject);
    }

    @Override
    public Object updateMerchandiseAddressByUser(String id, InformationRequest informationRequest) {
        Merchandise merchandiseOld = merchandiseRepository.findById(id).get();

        Address address = convertAddress.getAddressTow(
                ProvinceDto.builder()
                        .name(informationRequest.getProvince())
                        .code(informationRequest.getProvinceCode())
                        .build(),
                DistrictDto.builder()
                        .name(informationRequest.getDistrict())
                        .code(informationRequest.getDistrictCode())
                        .build(),
                WardDto.builder()
                        .name(informationRequest.getWard())
                        .code(informationRequest.getWardCode())
                        .build()
        );

        if(Objects.equals(informationRequest.getType(), "sender")){
            Information sender = merchandiseOld.getSender();
            sender.setName(informationRequest.getName());
            sender.setPhone(informationRequest.getPhone());
            sender.setAddress(address);
            merchandiseOld.setSender(sender);
        }else{
            Information receiver = merchandiseOld.getReceiver();
            receiver.setName(informationRequest.getName());
            receiver.setPhone(informationRequest.getPhone());
            receiver.setAddress(address);
            merchandiseOld.setReceiver(receiver);
        }
        return merchandiseRepository.save(merchandiseOld);
    }
}
