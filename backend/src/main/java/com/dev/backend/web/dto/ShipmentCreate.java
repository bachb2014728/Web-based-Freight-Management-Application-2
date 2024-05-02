package com.dev.backend.web.dto;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Driver;
import com.dev.backend.document.Router;
import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ShipmentCreate {
    private String id;
//    private Date estimatedTime;
    private String status;
    //    status: String, // 'Preparing', 'In Transit', 'Delivered'
    private Router router;
    private Store receivingStore;
    private Driver driver;
    private List<Batch> batches;
    private String note;
}
