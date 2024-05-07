package com.dev.backend.web.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
public class CreateMember {
    private String firstName;
    private String lastName;
    private String ward;
    private String district;
    private String province;
    private String codeId;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String gender;
    private String email;
    private String password;
}
