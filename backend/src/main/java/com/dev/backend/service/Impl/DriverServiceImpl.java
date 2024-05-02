package com.dev.backend.service.Impl;

import com.dev.backend.document.Car;
import com.dev.backend.document.Driver;
import com.dev.backend.repository.CarRepository;
import com.dev.backend.repository.DriverRepository;
import com.dev.backend.service.DriverService;
import com.dev.backend.web.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    @Override
    public List<DriverDto> all() {
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(this::mapDriverToDriverDto).collect(Collectors.toList());
    }
    public DriverDto mapDriverToDriverDto(Driver driver){
        return DriverDto.builder()
                .id(driver.getId())
                .name(driver.getName())
                .phone(driver.getPhone())
                .license(driver.getLicense())
                .car(driver.getCar())
                .address(driver.getAddress())
                .identifier(driver.getIdentifier())
                .shipments(driver.getShipments())
                .status(driver.getStatus())
                .build();
    }
    @Override
    public void save(DriverDto driverDto) {
        if(!driverRepository.existsByIdentifier(driverDto.getIdentifier())){
            Driver driver = mapDriverDtoToDriver(driverDto);
            if(Objects.equals(driver.getStatus(), "")){
                driver.setStatus("Available");
            }
            driver.setShipments(Collections.emptyList());
            Driver driverNew = driverRepository.save(driver);

            Car car = carRepository.findById(driverNew.getCar().getId()).get();
            car.setStatus("In Use");
            carRepository.save(car);
        }
    }

    @Override
    public DriverDto one(String driverId) {
        if(driverRepository.existsById(driverId)){
            Driver driver = driverRepository.findById(driverId).get();
            return mapDriverToDriverDto(driver);
        }
        return null;
    }

    @Override
    public void update(String driverId, DriverDto driverDto) {
        if(driverRepository.existsById(driverId)){
            Driver driver = driverRepository.findById(driverId).get();
            driver.setAddress(driverDto.getAddress());
            driver.setName(driverDto.getName());
            driver.setCar(driverDto.getCar());
            driver.setLicense(driverDto.getLicense());
            driver.setStatus(driverDto.getStatus());
            driver.setIdentifier(driverDto.getIdentifier());
            driver.setPhone(driverDto.getPhone());
            driverRepository.save(driver);
        }
        return;
    }

    @Override
    public void delete(String driverId) {
        if(driverRepository.existsById(driverId)){
            driverRepository.deleteById(driverId);
            return;
        }
        return;
    }

    @Override
    public List<DriverDto> findAllDriversHaveAvailable() {
        List<Driver> drivers = driverRepository.findAllByStatus("Available");
        if(drivers.isEmpty()){
            return new ArrayList<>();
        }else{
            return drivers.stream().map(this::mapDriverToDriverDto).collect(Collectors.toList());
        }
    }
    public Driver mapDriverDtoToDriver(DriverDto driver){
        return Driver.builder()
                .name(driver.getName())
                .phone(driver.getPhone())
                .license(driver.getLicense())
                .car(driver.getCar())
                .address(driver.getAddress())
                .identifier(driver.getIdentifier())
                .shipments(driver.getShipments())
                .status(driver.getStatus())
                .build();
    }
}
