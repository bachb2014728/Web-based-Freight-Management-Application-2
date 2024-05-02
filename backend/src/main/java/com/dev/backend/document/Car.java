package com.dev.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    private String licensePlate;  //    Biển số xe
    private double capacity; //    Trọng tải tối da
    private double load; //    Trọng tại hiện tại
    private String status;
    private String address;
//    Trạng thái hiện tại của xe.
//    Có thể là ‘Available’ (sẵn sàng), ‘In Use’ (đang sử dụng), hoặc‘Maintenance’ (đang bảo dưỡng).
}
