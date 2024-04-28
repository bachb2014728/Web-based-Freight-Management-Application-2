package com.dev.backend.web.dto;

import com.dev.backend.web.dto.location.Information;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EditMerchandise {
    private String name;
    private double price;
    private double weight;
    private List<byte[]> images;
    private Information sender;
    private Information receiver;
}
