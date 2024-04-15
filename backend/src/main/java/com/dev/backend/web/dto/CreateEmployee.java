package com.dev.backend.web.dto;

import com.dev.backend.document.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class CreateEmployee {
    private String firstName;
    private String lastName;
    private String ward;
    private String district;
    private String province;
    private String identifier;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String gender;
    private String email;
    private String password;
    private Role role;
    private boolean isEnabled;
}
