package com.dev.backend.config;

import com.dev.backend.document.Role;
import com.dev.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitRolesConfig {
    private final RoleRepository roleRepository;
    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            if(!roleRepository.existsByName("ADMIN")){
                List<String> privileges = Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");
                Role role = Role.builder()
                        .name("ADMIN")
                        .privileges(privileges)
                        .build();
                roleRepository.save(role);
            }
            if (!roleRepository.existsByName("USER")){
                Role role = Role.builder().name("USER").build();
                roleRepository.save(role);
            }
            if (!roleRepository.existsByName("STAFF")){
                List<String> privileges = Arrays.asList("CREATE", "READ", "UPDATE");
                Role role = Role.builder()
                        .name("STAFF")
                        .privileges(privileges)
                        .build();
                roleRepository.save(role);
            }
        };
    }
}
