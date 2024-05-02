package com.dev.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "drivers")
public class Driver {
    private String id;
    private String name;
    private String phone;
    //cccd
    private String identifier;
    private String status;
    private String address;
    //giấy phép lái xe
    private String license;
    @DBRef
    private List<Shipment> shipments;
    @DBRef
    private Car car;
//    Trạng thái hiện tại của tài xế. Có thể là ‘Available’ (sẵn sàng), ‘In Use’ (đang lái xe), hoặc‘Off Duty’ (nghỉ).
}
