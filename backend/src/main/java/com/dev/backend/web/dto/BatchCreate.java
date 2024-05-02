package com.dev.backend.web.dto;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Merchandise;
import com.dev.backend.document.Store;
import lombok.Data;

import java.util.List;

@Data
public class BatchCreate {
    private String type;
    private String destinationStore;
    private List<String> merchandises;
    private boolean status;
}
