package com.dev.backend.rest.dto;

import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MerchandiseUpdateRequest {
    private String id;
    private String name;
    private List<String> images;
    private double price;
    private double weight;
    private String status;
    private String store;
}
