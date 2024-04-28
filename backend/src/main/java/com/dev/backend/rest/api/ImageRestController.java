package com.dev.backend.rest.api;

import com.dev.backend.rest.dto.ImageRequest;
import com.dev.backend.service.ImageService;
import com.dev.backend.web.dto.ImageDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/uploadToMongoDB")
@RequiredArgsConstructor
public class ImageRestController {
    private final ImageService imageService;
    @PostMapping("")
    public ResponseEntity<ImageDataDto> uploadImageToMongoDB(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(imageService.uploadImageToMongoDB(file));
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
