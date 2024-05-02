package com.dev.backend.rest.dto;

import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class MerchandiseResponse {
    private String id;
    private String name;
    private List<byte[]> images;
    private double price;
    private double weight;
    private String nameSender;
    private String phoneSender;
    private String nameReceiver;
    private String phoneReceiver;
    private String addressSender;
    private String addressReceiver;
    private Store store;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedOn;
}
