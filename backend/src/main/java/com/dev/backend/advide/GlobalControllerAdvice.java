package com.dev.backend.advide;

import com.dev.backend.document.Role;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final UserRepository userRepository;
    @ModelAttribute("username")
    public String getUsername(Principal principal) {
        return (principal != null) ? principal.getName() : "";
    }
    @ModelAttribute("role")
    public String getRole(Principal principal){
        if (principal == null) {
            return "";
        }
        UserDocument user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user != null && !user.getRoles().isEmpty()) {
            List<String> roles = new ArrayList<>();
            for (Role item : user.getRoles()){
                roles.add(item.getName());
            }
            return roles.toString();
        }
        return "";
    }

}
