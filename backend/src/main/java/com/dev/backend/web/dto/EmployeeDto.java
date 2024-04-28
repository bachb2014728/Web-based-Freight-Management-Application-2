package com.dev.backend.web.dto;

import com.dev.backend.document.*;
import com.dev.backend.web.dto.location.Address;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class EmployeeDto {
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private String identifier; // Căn cước công dân
    private String phone;
    private UserDocument user;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Override
    public String toString() {
        return firstName + ' ' + lastName ;
    }
}
