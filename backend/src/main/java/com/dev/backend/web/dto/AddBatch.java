package com.dev.backend.web.dto;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Store;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AddBatch {
    private String id;
    private List<String> batches;
    private Store sendingStore;
}
