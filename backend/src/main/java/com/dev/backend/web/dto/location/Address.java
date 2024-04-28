package com.dev.backend.web.dto.location;

import com.dev.backend.document.District;
import com.dev.backend.document.Province;
import com.dev.backend.document.Ward;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private Ward ward;
    private District district;
    private Province province;

    @Override
    public String toString() {
        return ward.getName() + ", " + district.getName() + ", " + province.getName();
    }
}
