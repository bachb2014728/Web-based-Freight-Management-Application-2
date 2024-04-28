package com.dev.backend.service;

import com.dev.backend.rest.dto.ImageRequest;
import com.dev.backend.web.dto.ImageDataDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ImageDataDto uploadImageToMongoDB(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);

}
