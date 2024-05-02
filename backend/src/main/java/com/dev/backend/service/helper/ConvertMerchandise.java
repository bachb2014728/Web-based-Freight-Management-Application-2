package com.dev.backend.service.helper;

import com.dev.backend.document.Image;
import com.dev.backend.document.Merchandise;
import com.dev.backend.repository.ImageRepository;
import com.dev.backend.rest.dto.MerchandiseResponse;
import com.dev.backend.web.dto.MerchandiseDto;
import com.dev.backend.web.dto.MerchandiseUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConvertMerchandise {
    private final ConvertImage convertImage;
    private final ImageRepository imageRepository;
    public MerchandiseResponse mapMerchandiseToMerchandiseResponse(Merchandise merchandise){
        List<byte[]> images = new ArrayList<>();
        for(Image image : merchandise.getImages()){
            Optional<Image> imageItem = imageRepository.findById(image.getId());
            images.add(convertImage.decompressImage(imageItem.get().getImageData()));
        }
        return MerchandiseResponse.builder()
                .id(merchandise.getId())
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .weight(merchandise.getWeight())
                .images(images)
                .nameSender(merchandise.getSender().getName())
                .phoneSender(merchandise.getSender().getPhone())
                .addressSender(
                        merchandise.getSender().getAddress().toString()
                )
                .nameReceiver(merchandise.getReceiver().getName())
                .phoneReceiver(merchandise.getReceiver().getPhone())
                .addressReceiver(
                        merchandise.getReceiver().getAddress().toString()
                )
                .status(merchandise.getStatus())
                .createdAt(merchandise.getCreatedAt())
                .updatedOn(merchandise.getUpdatedOn())
                .build();
    }
    public MerchandiseDto mapMerchandiseToMerchandiseDto(Merchandise merchandise){
        List<byte[]> images = new ArrayList<>();
        for(Image image : merchandise.getImages()){
            Optional<Image> imageItem = imageRepository.findById(image.getId());
            images.add(convertImage.decompressImage(imageItem.get().getImageData()));
        }
        return MerchandiseDto.builder()
                .id(merchandise.getId())
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .images(images)
                .code(merchandise.getCode())
                .status(merchandise.getStatus())
                .store(merchandise.getStore())
                .status(merchandise.getStatus())
                .sender(merchandise.getSender())
                .receiver(merchandise.getReceiver())
                .createdAt(merchandise.getCreatedAt())
                .weight(merchandise.getWeight())
                .updatedOn(merchandise.getUpdatedOn())
                .build();
    }
    public MerchandiseUserDto mapMerchandiseToMerchandiseDtoForUser(Merchandise merchandise){
        List<byte[]> images = new ArrayList<>();
        for(Image image : merchandise.getImages()){
            Optional<Image> imageItem = imageRepository.findById(image.getId());
            images.add(convertImage.decompressImage(imageItem.get().getImageData()));
        }
        return MerchandiseUserDto.builder()
                .id(merchandise.getId())
                .name(merchandise.getName())
                .price(merchandise.getPrice())
                .images(images)
                .status(merchandise.getStatus())
                .store(merchandise.getStore().getId())
                .status(merchandise.getStatus())
                .sender(merchandise.getSender())
                .receiver(merchandise.getReceiver())
                .createdAt(merchandise.getCreatedAt())
                .weight(merchandise.getWeight())
                .updatedOn(merchandise.getUpdatedOn())
                .build();
    }
}
