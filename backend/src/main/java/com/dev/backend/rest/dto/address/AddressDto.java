package com.dev.backend.rest.dto.address;

import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.WardDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressDto {
    private ProvinceDto province;
    private DistrictDto district;
    private WardDto ward;
}
