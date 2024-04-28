package com.dev.backend.rest.dto.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InformationRequest {
    private String name;
    private String phone;

    private String province;
    private String provinceCode;

    private String district;
    private String districtCode;

    private String ward;
    private String wardCode;

    private String type;

}
