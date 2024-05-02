package com.dev.backend.web.dto;

import com.dev.backend.document.Image;
import com.dev.backend.document.Store;
import com.dev.backend.web.dto.location.DistrictDto;
import com.dev.backend.web.dto.location.ProvinceDto;
import com.dev.backend.web.dto.location.WardDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class CreateMerchandise {
    private String name;
    private List<String> images;
    private double price;
    private double weight;
    private String store;

    private String status;
    private String nameSender;
    private String phoneSender;
    private ProvinceDto provinceSender;
    private DistrictDto districtSender;
    private WardDto wardSender;

    private String nameReceiver;
    private String phoneReceiver;
    private ProvinceDto provinceReceiver;
    private DistrictDto districtReceiver;
    private WardDto wardReceiver;
}
