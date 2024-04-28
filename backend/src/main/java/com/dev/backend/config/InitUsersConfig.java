package com.dev.backend.config;

import com.dev.backend.document.*;
import com.dev.backend.repository.*;
import com.dev.backend.service.helper.ConvertAddress;
import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.ProvinceDto;
import com.dev.backend.web.dto.location.WardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@DependsOn("initRoles")
public class InitUsersConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final ConvertAddress convertAddress;
    @Bean
    public CommandLineRunner initUsers(PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("adminCT@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminCT@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Lê")
                        .lastName("Vân Anh")
                        .phone("0920071122")
                        .identifier("1234 0987 34534")
                        .gender("Nữ")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Cần Thơ").code("92").build(),
                                        DistrictDto.builder().name("Ninh Kiều").code("916").build(),
                                        WardDto.builder().name("Xuân Khánh").code("31144").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }
            if (!userRepository.existsByEmail("adminHCM@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminHCM@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Nguyễn")
                        .lastName("Phương Anh")
                        .phone("0920071133")
                        .identifier("1234 0987 34537")
                        .gender("Nữ")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Hồ Chí Minh").code("79").build(),
                                        DistrictDto.builder().name("Quận 11").code("772").build(),
                                        WardDto.builder().name("Phường 2").code("27250").build()
                                        )
                        )
                        .build();
                employeeRepository.save(employee);
            }

            if (!userRepository.existsByEmail("adminHN@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminHN@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Võ")
                        .lastName("Ngọc Duyên")
                        .phone("0920071177")
                        .identifier("1234 0987 34512")
                        .gender("Nữ")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Hà Nội").code("1").build(),
                                        DistrictDto.builder().name("Cầu Giấy").code("5").build(),
                                        WardDto.builder().name("Quan Hoa").code("169").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }

            // Hậu Giang
            if (!userRepository.existsByEmail("adminHG@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminHG@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Trần")
                        .lastName("Văn Ba")
                        .phone("0920071132")
                        .identifier("1234 0987 34500")
                        .gender("Nam")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Hậu Giang").code("93").build(),
                                        DistrictDto.builder().name("Phụng Hiệp").code("934").build(),
                                        WardDto.builder().name("Bình Thành").code("31402").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }

// Vĩnh Long
            if (!userRepository.existsByEmail("adminVL@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminVL@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Tưởng")
                        .lastName("Thanh Tri")
                        .phone("0920070000")
                        .identifier("1234 0987 12345")
                        .gender("Nam")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Vĩnh Long").code("86").build(),
                                        DistrictDto.builder().name("Bình Minh").code("861").build(),
                                        WardDto.builder().name("Mỹ Hòa").code("29815").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }

// Đồng Tháp
            if (!userRepository.existsByEmail("adminDT@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminDT@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Lê")
                        .lastName("Thanh Hải")
                        .phone("0920073434")
                        .identifier("1234 0987 34589")
                        .gender("Nam")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Đồng Tháp").code("87").build(),
                                        DistrictDto.builder().name("Châu Thành").code("877").build(),
                                        WardDto.builder().name("Tân Nhuận Đông").code("30253").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }

// Cà Mau
            if (!userRepository.existsByEmail("adminCM@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminCM@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Huỳnh")
                        .lastName("Mỹ Hoa")
                        .phone("0920071806")
                        .identifier("1234 0987 45678")
                        .gender("Nữ")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Cà Mau").code("96").build(),
                                        DistrictDto.builder().name("Cái Nước").code("969").build(),
                                        WardDto.builder().name("Lương Thế Trân").code("32131").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }

// Bình Dương
            if (!userRepository.existsByEmail("adminBD@gmail.com")) {
                Role role = roleRepository.findByName("ADMIN").get();
                UserDocument user = UserDocument.builder()
                        .email("adminBD@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Collections.singletonList(role))
                        .build();
                UserDocument userNew = userRepository.save(user);
                Employee employee = Employee.builder()
                        .user(userNew)
                        .firstName("Nguyễn")
                        .lastName("Mai Lan")
                        .phone("0920071314")
                        .identifier("1234 0987 78900")
                        .gender("Nữ")
                        .address(
                                convertAddress.getAddressTow(
                                        ProvinceDto.builder().name("Bình Dương").code("74").build(),
                                        DistrictDto.builder().name("Bến Cát").code("721").build(),
                                        WardDto.builder().name("Chánh Phú Hòa").code("25937").build()
                                )
                        )
                        .build();
                employeeRepository.save(employee);
            }
        };
    }
}
