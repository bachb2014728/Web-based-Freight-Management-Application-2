package com.dev.backend.web.dto.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinceDto {
    private String code;
    private String name;
}
