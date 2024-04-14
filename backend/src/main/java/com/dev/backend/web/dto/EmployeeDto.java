package com.dev.backend.web.dto;

import com.dev.backend.document.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private Address address;
    private String identifier; // Căn cước công dân
    private String phone;
    private UserDocument user;
}
