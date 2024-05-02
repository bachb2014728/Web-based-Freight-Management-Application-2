package com.dev.backend.service.Impl;

import com.dev.backend.document.Router;
import com.dev.backend.repository.RouterRepository;
import com.dev.backend.service.RouterService;
import com.dev.backend.web.dto.location.Location;
import com.dev.backend.web.dto.RouterDto;
import com.dev.backend.web.dto.RouterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouterServiceImpl implements RouterService {
    private final RouterRepository routerRepository;
    @Override
    public void deleteRouterById(String routerId) {
        if (routerRepository.existsById(routerId)){
            routerRepository.deleteById(routerId);
        }
        return;
    }
    @Override
    public RouterDto findRouterById(String routerId) {
        Router router = routerRepository.getRouterById(routerId).get();

        return mapRouterToRouterDto(router);
    }
    @Override
    public RouterDto updateRouter(RouterRequest routerRequest, String routerId) {
        Router router = routerRepository.findById(routerId).get();
        router.setName(routerRequest.getName());
        router.setStartPoint(convertLocation(routerRequest.getWardsStart(),routerRequest.getDistrictsStart(),routerRequest.getProvincesStart()));
        router.setEndPoint(convertLocation(routerRequest.getWardsEnd(),routerRequest.getDistrictsEnd(),routerRequest.getProvincesEnd()));
        router.setDistance(routerRequest.getDistance());
        router.setTime(routerRequest.getTime());
        router.setStatus(routerRequest.isStatus());
        router.setNotes(routerRequest.getNotes());
        Router routerNew = routerRepository.save(router);
        return mapRouterToRouterDto(routerNew);
    }

    @Override
    public List<RouterDto> findAllRouters() {
        List<Router> routers = routerRepository.findAll();
        return routers.stream().map(this::mapRouterToRouterDto).collect(Collectors.toList());
    }
    public RouterDto mapRouterToRouterDto(Router router){
        return RouterDto.builder()
                .Id(router.getId())
                .name(router.getName())
                .distance(router.getDistance())
                .startPoint(router.getStartPoint().toString())
                .endPoint(router.getEndPoint().toString())
                .time(router.getTime())
                .status(router.isStatus())
                .notes(router.getNotes())
                .build();
    }

    @Override
    public void saveRouter(RouterRequest routerRequest) {
        Router router = Router.builder()
                .name(routerRequest.getName())
                .startPoint(convertLocation(routerRequest.getWardsStart(),routerRequest.getDistrictsStart(),routerRequest.getProvincesStart()))
                .endPoint(convertLocation(routerRequest.getWardsEnd(),routerRequest.getDistrictsEnd(),routerRequest.getProvincesEnd()))
                .time(routerRequest.getTime())
                .distance(routerRequest.getDistance())
                .status(routerRequest.isStatus())
                .notes(routerRequest.getNotes())
                .build();
        routerRepository.save(router);
    }

    @Override
    public List<RouterDto> findAllRoutersHaveActive() {
        List<Router> routers = routerRepository.findAllByStatus(true);
        return routers.stream().map(this::mapRouterToRouterDto).collect(Collectors.toList());
    }

    private Location convertLocation(String ward, String district, String province) {
        Location location = Location.builder()
                .ward(ward)
                .district(district)
                .province(province)
                .build();
        return location;
    }
}
