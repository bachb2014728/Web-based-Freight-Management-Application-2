package com.dev.backend.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ImageRequest {
    private List<MultipartFile> images;
}
