package com.dev.backend.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Information {
    private String name;
    private String phone;
    private Address address;
}
