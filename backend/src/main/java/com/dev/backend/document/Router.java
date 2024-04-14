package com.dev.backend.document;

import com.dev.backend.web.dto.Location;
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
@Document(collection = "routers")
public class Router {
    @Id
    private String id;
    private String name;
    private Location startPoint; //điểm bắt đầu
    private Location endPoint; //điểm kết thúc
    private double distance;  // khoảng cách
    private double time; //thời gian dự kiến
    private boolean status;  //trạng thái con đường: hoạt động - không họạt động
    private String notes; // ghi chú
}