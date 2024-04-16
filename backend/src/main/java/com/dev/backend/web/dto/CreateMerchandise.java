package com.dev.backend.web.dto;

import com.dev.backend.document.Image;
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
    private String nameSender;
    private String phoneSender;
    private String provinceSender;
    private String districtSender;
    private String wardSender;
    private String nameReceiver;
    private String phoneReceiver;
    private String provinceReceiver;
    private String districtReceiver;
    private String wardReceiver;
}
