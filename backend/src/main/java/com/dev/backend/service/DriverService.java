package com.dev.backend.service;

import com.dev.backend.web.dto.DriverDto;

import java.util.List;

public interface DriverService {
    List<DriverDto> all();

    void save(DriverDto driverDto);

    DriverDto one(String driverId);

    void update(String driverId, DriverDto driverDto);

    void delete(String driverId);

    List<DriverDto> findAllDriversHaveAvailable();

}
