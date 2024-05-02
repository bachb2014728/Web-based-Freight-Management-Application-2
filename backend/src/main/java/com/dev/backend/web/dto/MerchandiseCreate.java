package com.dev.backend.web.dto;

import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.ProvinceDto;
import com.dev.backend.web.dto.location.WardDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MerchandiseCreate {
    private String name;
    private List<String> images;
    private double price;
    private double weight;
    private String store;

    private String status;

    private String nameSender;
    private String phoneSender;

    private String nameReceiver;
    private String phoneReceiver;
}
