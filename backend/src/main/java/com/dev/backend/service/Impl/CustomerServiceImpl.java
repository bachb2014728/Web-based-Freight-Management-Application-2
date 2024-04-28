package com.dev.backend.service.Impl;

import com.dev.backend.document.*;
import com.dev.backend.jwt.JwtService;
import com.dev.backend.repository.CustomerRepository;
import com.dev.backend.repository.UserRepository;
import com.dev.backend.rest.dto.CustomerDto;
import com.dev.backend.rest.dto.CustomerRequest;
import com.dev.backend.service.CustomerService;
import com.dev.backend.service.helper.ConvertAddress;
import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.ProvinceDto;
import com.dev.backend.web.dto.location.WardDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ConvertAddress convertAddress;
    @Override
    public CustomerDto getInfoCustomer(HttpServletRequest request) {
        UserDocument user = getUserDocument(request);

        return mapCustomerToCustomerDto(customerRepository.findByUser(user));
    }
    private CustomerDto mapCustomerToCustomerDto(Customer customer){
        return CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .gender(customer.getGender())
                .date(customer.getDate())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .codeId(customer.getCodeId())
                .user(customer.getUser())
                .build();
    }
    @Override
    public Customer updateInfo(HttpServletRequest request, CustomerRequest customerRequest) {
        UserDocument user = getUserDocument(request);
        Customer customer = customerRepository.findByUser(user);
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setCodeId(customerRequest.getCodeId());
        customer.setGender(customerRequest.getGender());
        customer.setPhone(customerRequest.getPhone());
        customer.setDate(customerRequest.getDate());
        customer.setAddress(
                convertAddress.getAddress(
                        ProvinceDto.builder().code(customerRequest.getProvinceCode()).name(customerRequest.getProvince()).build(),
                        DistrictDto.builder().code(customerRequest.getDistrictCode()).name(customerRequest.getDistrict()).build(),
                        WardDto.builder().code(customerRequest.getWardCode()).name(customerRequest.getWard()).build()
                )
        );

        return customerRepository.save(customer);

    }

    private UserDocument getUserDocument(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.length() <= 7) {
            return null;
        }
        String token = authHeader.substring(7);
        String email = jwtService.extraUsername(token);
        return userRepository.findByEmail(email).get(); 
    }

}
