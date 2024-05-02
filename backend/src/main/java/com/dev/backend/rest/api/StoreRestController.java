package com.dev.backend.rest.api;

import com.dev.backend.rest.dto.StoreDto;
import com.dev.backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreRestController {
    private final StoreService storeService;

    @GetMapping("")
    public ResponseEntity<List<StoreDto>> getAll(){
        return ResponseEntity.ok(storeService.getAllStore());
    }
}
