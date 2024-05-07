package com.dev.backend.rest.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String provinceId;
    private String province;
    private String districtId;
    private String district;
    private String wardId;
    private String ward;
    private String password;
    private String passwordConfirm;
}