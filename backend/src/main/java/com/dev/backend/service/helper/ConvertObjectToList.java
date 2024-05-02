package com.dev.backend.service.helper;

import com.dev.backend.document.Batch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConvertObjectToList {
    public List<String> shipment(List<Batch> batches){
        List<String> data = new ArrayList<>();
        for (Batch batch : batches){
            data.add(batch.getId());
        }
        return data;
    }
}
