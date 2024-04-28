package com.dev.backend.web.dto.location;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private String ward;
    private String district;
    private String province;

    @Override
    public String toString() {
        return ward + ", " + district + ", " + province ;
    }
}

