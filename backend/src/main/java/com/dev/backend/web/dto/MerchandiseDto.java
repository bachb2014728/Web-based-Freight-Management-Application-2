package com.dev.backend.web.dto;

import com.dev.backend.document.Image;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MerchandiseDto {
    private String id;
    private String name;
    private List<byte[]> images;
    private double price;
    private double weight;
    private String status;
    private Information sender ; //người gửi
    private Information receiver; //người nhận
    private LocalDateTime createdAt;
    private LocalDateTime updatedOn;
}