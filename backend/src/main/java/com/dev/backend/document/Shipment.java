package com.dev.backend.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//Vận chuyển
@Document(collection = "shipments")
public class Shipment {
    @Id
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  estimatedTime;
    private String status;
    private double totalWeight;
    private String note;
//    status: String, // 'Preparing', 'In Transit', 'Delivered'
    @DBRef(lazy = true)
    private List<Batch> batches;
    @DBRef(lazy = true)
    private Router router;
    @DBRef(lazy = true)
    private Driver driver;
    @DBRef(lazy = true)
    private Store sendingStore;
    @DBRef(lazy = true)
    private Store receivingStore;

    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonFormat(pattern="dd-MM-yyy HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updatedOn;
}
