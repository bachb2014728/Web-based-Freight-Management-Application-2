package com.dev.backend.config;

import com.dev.backend.document.Role;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.RoleRepository;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@DependsOn("initRoles")
public class InitUsersConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Bean
    public CommandLineRunner initUsers(PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@gmail.com")) {
                List<Role> roles = roleRepository.findAll();
                UserDocument user = UserDocument.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
            }
            if (!userRepository.existsByEmail("staff@gmail.com")){
                UserDocument user = UserDocument.builder()
                        .email("staff@gmail.com")
                        .password(passwordEncoder.encode("staff"))
                        .roles(Collections.singletonList(roleRepository.findByName("STAFF").get()))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
