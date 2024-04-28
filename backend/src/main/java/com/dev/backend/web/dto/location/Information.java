package com.dev.backend.web.dto.location;

import com.dev.backend.web.dto.location.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Information {
    private String name;
    private String phone;
    private Address address;
}
