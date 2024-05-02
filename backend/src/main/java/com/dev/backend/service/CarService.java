package com.dev.backend.service;

import com.dev.backend.document.Car;
import com.dev.backend.web.dto.CarDto;

import java.util.List;

public interface CarService {
    void save(CarDto carDto);

    List<CarDto> all();

    CarDto findOne(String carId);

    void update(CarDto carDto, String carId);

    void delete(String carId);

    List<CarDto> getAvailable();

}
