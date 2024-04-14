package com.dev.backend.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouterDto {
    private String Id;
    private String name;
    private String startPoint;
    private String endPoint;
    private double distance;
    private double time;
    private boolean status;
    private String notes;
}