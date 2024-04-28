package com.dev.backend.rest.api;

import com.dev.backend.document.Customer;
import com.dev.backend.rest.dto.CustomerDto;
import com.dev.backend.rest.dto.CustomerRequest;
import com.dev.backend.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;
    @GetMapping("/")
    private ResponseEntity<CustomerDto> get(HttpServletRequest request){
        return ResponseEntity.ok(customerService.getInfoCustomer(request));
    }
    @PutMapping("/update")
    private ResponseEntity<Customer> updateInfo(HttpServletRequest request, @RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.updateInfo(request, customerRequest));
    }
}
