package com.dev.backend.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouterRequest {
    private String id;
    private String name;
    private String provincesStart;
    private String districtsStart;
    private String wardsStart;
    private String provincesEnd;
    private String districtsEnd;
    private String wardsEnd;
    private double distance;
    private double time;
    private boolean status;
    private String notes;
}
