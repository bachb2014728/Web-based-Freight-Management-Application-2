package com.dev.backend.web.util;

import org.springframework.context.annotation.Configuration;

import java.util.Base64;
@Configuration
public class ImageUtil {
    public static String convertToBase64String(byte[] imageData) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
    }
}

