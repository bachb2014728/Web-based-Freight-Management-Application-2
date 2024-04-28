package com.dev.backend.rest.dto;

import com.dev.backend.document.Merchandise;
import com.dev.backend.document.UserDocument;
import com.dev.backend.web.dto.location.Address;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Builder
public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private Date date;
    private String codeId;
    private Address address;
    private List<Merchandise> merchandises;
    private UserDocument user;
}
