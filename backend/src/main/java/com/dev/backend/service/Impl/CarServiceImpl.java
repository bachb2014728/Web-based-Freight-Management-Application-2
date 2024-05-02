package com.dev.backend.service.Impl;

import com.dev.backend.document.Car;
import com.dev.backend.repository.CarRepository;
import com.dev.backend.service.CarService;
import com.dev.backend.web.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Override
    public void save(CarDto carDto) {
        if(!carRepository.existsByLicensePlate(carDto.getLicensePlate())){
            Car car = mapCarDtoToCar(carDto);
            carRepository.save(car);
        }else {
            return;
        }
    }

    private Car mapCarDtoToCar(CarDto carDto) {
        return Car.builder()
                .load(carDto.getLoad())
                .licensePlate(carDto.getLicensePlate())
                .capacity(carDto.getCapacity())
                .address(carDto.getAddress())
                .status(carDto.getStatus())
                .build();
    }

    @Override
    public List<CarDto> all() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(this::mapCarToCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto findOne(String carId) {
        Car car = carRepository.findById(carId).get();
        return mapCarToCarDto(car);
    }

    @Override
    public void update(CarDto carDto, String carId) {
        if (carRepository.existsById(carId)){
            Car car = carRepository.findById(carId).get();
            car.setAddress(carDto.getAddress());
            car.setLoad(carDto.getLoad());
            car.setCapacity(carDto.getCapacity());
            car.setLicensePlate(carDto.getLicensePlate());
            car.setStatus(carDto.getStatus());
            carRepository.save(car);
        }
        return;
    }

    @Override
    public void delete(String carId) {
        if(carRepository.existsById(carId)){
            carRepository.deleteById(carId);
        }
        return;
    }

    @Override
    public List<CarDto> getAvailable() {
        List<Car> cars = carRepository.findAllByStatus("Available");
        return cars.stream().map(this::mapCarToCarDto).collect(Collectors.toList());
    }

    private CarDto mapCarToCarDto(Car carDto) {
        return CarDto.builder()
                .id(carDto.getId())
                .load(carDto.getLoad())
                .licensePlate(carDto.getLicensePlate())
                .capacity(carDto.getCapacity())
                .address(carDto.getAddress())
                .status(carDto.getStatus())
                .build();
    }
}
