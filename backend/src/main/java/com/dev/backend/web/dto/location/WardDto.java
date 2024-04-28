package com.dev.backend.web.dto.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WardDto {
    private String code;
    private String name;
}
