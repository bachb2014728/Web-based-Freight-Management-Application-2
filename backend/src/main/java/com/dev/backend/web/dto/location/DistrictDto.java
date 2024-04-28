package com.dev.backend.web.dto.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictDto {
    private String code;
    private String name;
}
