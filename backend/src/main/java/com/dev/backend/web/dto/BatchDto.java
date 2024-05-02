package com.dev.backend.web.dto;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Merchandise;
import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchDto {
    private String id;
    private String type;
    private double totalWeight;
    private double totalPrice;
    private Store sourceStore;
    private Store destinationStore;
    private List<Merchandise> merchandises;
    private Employee creator;
    private String status;
}
