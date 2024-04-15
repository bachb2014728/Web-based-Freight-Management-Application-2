package com.dev.backend.config;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
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
    private final EmployeeRepository employeeRepository;
    @Bean
    public CommandLineRunner initUsers(PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .build();
                employeeRepository.save(employee);
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
