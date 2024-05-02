package com.dev.backend.web.dto;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Driver;
import com.dev.backend.document.Router;
import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder
public class ShipmentUpdate {
    private String id;
//    private Date estimatedTime;
    private String status;
//    private double totalWeight;
    private String note;
    //    status: String, // 'Preparing', 'In Transit', 'Delivered'
    private List<Batch> batches;
    private Router router;
    private Driver driver;
    private Store sendingStore;
    private Store receivingStore;
}
