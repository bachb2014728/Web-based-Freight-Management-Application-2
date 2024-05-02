package com.dev.backend.repository;

import com.dev.backend.document.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends MongoRepository<Driver,String> {
    boolean existsByIdentifier(String identifier);
    List<Driver> findAllByStatus(String status);
}
