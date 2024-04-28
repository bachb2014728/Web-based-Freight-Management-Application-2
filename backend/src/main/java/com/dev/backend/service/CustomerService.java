package com.dev.backend.service;

import com.dev.backend.document.Customer;
import com.dev.backend.rest.dto.CustomerDto;
import com.dev.backend.rest.dto.CustomerRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface CustomerService {
    CustomerDto getInfoCustomer(HttpServletRequest request);

    Customer updateInfo(HttpServletRequest request, CustomerRequest customerRequest);
}
