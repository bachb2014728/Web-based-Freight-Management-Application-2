package com.dev.backend.repository;

import com.dev.backend.document.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car,String> {
    boolean existsByLicensePlate(String license);

    Car findByLicensePlate(String license);

    List<Car> findAllByStatus(String status);
}
