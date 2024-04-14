package com.dev.backend.service;

import com.dev.backend.web.dto.RouterDto;
import com.dev.backend.web.dto.RouterRequest;

import java.util.List;

public interface RouterService {
    void deleteRouterById(String routerId);
    RouterDto findRouterById(String routerId);
    RouterDto updateRouter(RouterRequest routerRequest, String routerId);
    List<RouterDto> findAllRouters();
    void saveRouter(RouterRequest routerRequest);
}
