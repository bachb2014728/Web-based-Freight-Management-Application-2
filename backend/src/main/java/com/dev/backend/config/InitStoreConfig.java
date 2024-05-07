package com.dev.backend.config;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Store;
import com.dev.backend.document.UserDocument;
import com.dev.backend.repository.EmployeeRepository;
import com.dev.backend.repository.StoreRepository;
import com.dev.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@RequiredArgsConstructor
@DependsOn("initUsers")
public class InitStoreConfig {
    private final StoreRepository storeRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    @Bean
    public CommandLineRunner initStore(){
        return args -> {
            if(!storeRepository.existsByName("Gear5 Post Cần Thơ")){
                UserDocument user = userRepository.findByEmail("adminCT@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Cần Thơ")
                        .phone("0918070411")
                        .address("Đ.30/04, xã Xuân Khánh, huyện Ninh Kiều, thành phố Cần Thơ")
                        .employee(employee)
                        .province("Cần Thơ")
                        .build();
                storeRepository.save(store);
            }
            if(!storeRepository.existsByName("Gear5 Post Hồ Chí Minh")){
                UserDocument user = userRepository.findByEmail("adminHCM@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Hồ Chí Minh")
                        .phone("0918070411")
                        .address("Đ.Trần Duy Hưng, phường 2, quận 11, thành phố Hồ Chí Minh")
                        .employee(employee)
                        .province("Hồ Chí Minh")
                        .build();
                storeRepository.save(store);
            }
            if(!storeRepository.existsByName("Gear5 Post Hà Nội")){
                UserDocument user = userRepository.findByEmail("adminHN@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Hà Nội")
                        .phone("0918070411")
                        .address("Đ.Thống Nhất, xã Quan Hoa, huyện Cầu Giấy, thủ đô Hà Nội")
                        .employee(employee)
                        .province("Hà Nội")
                        .build();
                storeRepository.save(store);
            }

            // Hậu Giang
            if (!storeRepository.existsByName("Gear5 Post Hậu Giang")) {
                UserDocument user = userRepository.findByEmail("adminHG@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Hậu Giang")
                        .phone("0918070411")
                        .address("Đ.Lý Thường Kiệt, xã Bình Thạnh, huyện Phụng Hiệp, thành phố Hậu Giang")
                        .employee(employee)
                        .province("Hậu Giang")
                        .build();
                storeRepository.save(store);
            }

// Vĩnh Long
            if (!storeRepository.existsByName("Gear5 Post Vĩnh Long")) {
                UserDocument user = userRepository.findByEmail("adminVL@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Vĩnh Long")
                        .phone("0918070411")
                        .address("Đ.3/2, xã Mỹ Hòa, huyện Bình Minh, thành phố Vĩnh Long")
                        .employee(employee)
                        .province("Vĩnh Long")
                        .build();
                storeRepository.save(store);
            }

// Đồng Tháp
            if (!storeRepository.existsByName("Gear5 Post Đồng Tháp")) {
                UserDocument user = userRepository.findByEmail("adminDT@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Đồng Tháp")
                        .phone("0918070411")
                        .address("Đ.Trần Quốc Toản, xã Tân Nhuận Đông, huyện Châu Thành, tỉnh Đồng Tháp")
                        .employee(employee)
                        .province("Đồng Tháp")
                        .build();
                storeRepository.save(store);
            }

// Cà Mau
            if (!storeRepository.existsByName("Gear5 Post Cà Mau")) {
                UserDocument user = userRepository.findByEmail("adminCM@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Cà Mau")
                        .phone("0918070411")
                        .address("Đ.Yết Kiêu, xã Lương Thế Trân, huyện Cái Nước, tỉnh Cà Mau")
                        .employee(employee)
                        .province("Cà Mau")
                        .build();
                storeRepository.save(store);
            }

// Bình Dương
            if (!storeRepository.existsByName("Gear5 Post Bình Dương")) {
                UserDocument user = userRepository.findByEmail("adminBD@gmail.com").get();
                Employee employee = employeeRepository.findByUser(user);
                Store store = Store.builder()
                        .name("Gear5 Post Bình Dương")
                        .phone("0918070411")
                        .address("Đ.Thống Nhất, xã Chánh Phú Hòa, huyện Bến Cát, tỉnh Bình Dương")
                        .employee(employee)
                        .province("Bình Dương")
                        .build();
                storeRepository.save(store);
            }

        };
    }
}
