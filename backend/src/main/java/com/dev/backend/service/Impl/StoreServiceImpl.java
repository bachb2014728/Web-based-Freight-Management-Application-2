package com.dev.backend.service.Impl;

import com.dev.backend.document.Store;
import com.dev.backend.repository.StoreRepository;
import com.dev.backend.rest.dto.StoreDto;
import com.dev.backend.service.StoreService;
import com.dev.backend.service.helper.ConvertStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ConvertStore convertStore;

    @Override
    public List<StoreDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(this::mapStoreToStoreDto).collect(Collectors.toList());
    }

    @Override
    public List<StoreDto> findAllStores(Principal principal) {
        List<Store> stores = storeRepository.findAll();
        stores.removeIf(store -> store.equals(convertStore.getStoreByEmail(principal.getName())));
        return stores.stream().map(this::mapStoreToStoreDto).collect(Collectors.toList());
    }

    @Override
    public List<StoreDto> getAllStoreByEmployee(Principal principal) {
        List<Store> stores = storeRepository.findAll();
        stores.removeIf(store -> store.equals(convertStore.getStoreByEmail(principal.getName())));
        return stores.stream().map(this::mapStoreToStoreDto).collect(Collectors.toList());
    }

    public StoreDto mapStoreToStoreDto(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .phone(store.getPhone())
                .address(store.getAddress())
                .province(store.getProvince())
                .build();
    }
}
