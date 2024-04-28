package com.dev.backend.service.Impl;

import com.dev.backend.document.Image;
import com.dev.backend.repository.ImageRepository;
import com.dev.backend.rest.dto.ImageRequest;
import com.dev.backend.service.ImageService;
import com.dev.backend.web.dto.ImageDataDto;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public ImageDataDto uploadImageToMongoDB(MultipartFile file) throws IOException {
        Image imageData = imageRepository.save(
                Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(compressImage(file.getBytes()))
                        .build());
        return mapImageDataToImageDataDto(imageData);
    }

    @Override
    public byte[] downloadImage(String fileName) {
        return new byte[0];
    }
    public static byte[] compressImage(byte[] data) {
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



    public static byte[] decompressImage(byte[] data) {
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

    public ImageDataDto mapImageDataToImageDataDto(Image image){
        return ImageDataDto.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .imageData(image.getImageData())
                .build();
    }
}
