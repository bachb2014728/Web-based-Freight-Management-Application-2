package com.dev.backend.rest.dto;

import com.dev.backend.web.dto.location.Information;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class MerchandiseUpdateRequest {
    private String id;
    private String name;
    private List<String> images;
    private double price;
    private double weight;
    private String status;
}
