package com.dev.backend.rest.dto;

import com.dev.backend.document.Employee;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String province;
}
