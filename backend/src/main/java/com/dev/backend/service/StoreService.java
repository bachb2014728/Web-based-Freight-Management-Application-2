package com.dev.backend.service;

import com.dev.backend.rest.dto.StoreDto;

import java.security.Principal;
import java.util.List;

public interface StoreService {
    List<StoreDto> getAllStore();

    List<StoreDto> findAllStores(Principal principal);

    List<StoreDto> getAllStoreByEmployee(Principal principal);

}
