package com.dev.backend.service;

import com.dev.backend.document.Batch;
import com.dev.backend.web.dto.BatchCreate;
import com.dev.backend.web.dto.BatchDto;

import java.security.Principal;
import java.util.List;

public interface BatchService {
    List<BatchDto> all(Principal principal);

    Batch save(BatchCreate batch, Principal principal);

    BatchCreate oneBatchUpdate(String batchId);

    void update(String batchId, BatchCreate batch, Principal principal);

    void delete(String batchId,Principal principal);

    List<BatchDto> findAllByStore(Principal principal);
}
