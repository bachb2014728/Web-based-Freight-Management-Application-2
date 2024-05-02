package com.dev.backend.web.dto;

import com.dev.backend.document.Car;
import com.dev.backend.document.Shipment;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class DriverDto {
    private String id;
    private String name;
    private String phone;
    private String identifier;
    private String status;
    private String license;
    private String address;
    private List<Shipment> shipments;
    private Car car;
}
