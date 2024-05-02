package com.dev.backend.web.util;

import com.dev.backend.document.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyUtil {
    public static boolean checkBatch(List<String> batches , Batch batch){
        boolean status = false;
        for(String item : batches){
            if (item.equals(batch.getId())) {
                status = true;
                break;
            }
        }
        return status;
    }
}
