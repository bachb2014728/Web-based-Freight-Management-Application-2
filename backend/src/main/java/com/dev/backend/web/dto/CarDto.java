package com.dev.backend.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private String id;
    private String licensePlate;
    private double capacity;
    private double load;
    private String status;
    private String address;
}
