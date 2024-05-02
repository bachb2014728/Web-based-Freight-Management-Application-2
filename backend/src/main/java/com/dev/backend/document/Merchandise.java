package com.dev.backend.document;

import com.dev.backend.web.dto.location.Information;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "merchandises")
public class Merchandise {
    @Id
    private String id;
    @DBRef(lazy = true)
    private UserDocument code;
    private String name;
    @DBRef(lazy = true)
    private List<Image> images;
    private double price;
    private double weight;
    private String status;
    private Information sender ; //người gửi
    private Information receiver; //người nhận
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedOn;
    @DBRef(lazy = true)
    private Store store;
}
