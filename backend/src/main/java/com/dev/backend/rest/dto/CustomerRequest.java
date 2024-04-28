package com.dev.backend.rest.dto;

import com.dev.backend.rest.dto.address.AddressDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private Date date;
    private String codeId;
    private String provinceCode;
    private String province;
    private String districtCode;
    private String district;
    private String wardCode;
    private String ward;
}

