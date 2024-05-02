package com.dev.backend.web.dto;

import com.dev.backend.web.dto.location.Information;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MerchandiseUserDto {
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
    private String store;
}
