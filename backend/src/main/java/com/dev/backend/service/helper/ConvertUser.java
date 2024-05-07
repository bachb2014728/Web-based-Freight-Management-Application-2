package com.dev.backend.service.helper;

import com.dev.backend.document.Image;
import com.dev.backend.document.Role;
import com.dev.backend.document.UserDocument;
import com.dev.backend.jwt.JwtService;
import com.dev.backend.repository.ImageRepository;
import com.dev.backend.repository.UserRepository;
import com.dev.backend.web.dto.CreateMerchandise;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConvertUser {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    public UserDocument getUserFromToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String email = jwtService.extraUsername(token);
        return userRepository.findByEmail(email).orElseThrow();
    }
    public List<Image> convertImageData(CreateMerchandise merchandise){
        List<Image> images = new ArrayList<>();
        for (String imageId : merchandise.getImages()){
            if(imageRepository.existsById(imageId)){
                images.add(imageRepository.findById(imageId).get());
            }
        }
        return images;
    }
    public String getRoleFromEmail(String email){
        UserDocument userDocument = userRepository.findByEmail(email).get();
        Role role = userDocument.getRoles().get(0);
        return role.getName();
    }
}
