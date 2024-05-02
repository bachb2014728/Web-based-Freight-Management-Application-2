package com.dev.backend.web.dto;

import com.dev.backend.document.Shipment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentView {
    private String name;
    private Shipment shipment;
}
