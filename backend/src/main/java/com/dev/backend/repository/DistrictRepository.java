package com.dev.backend.repository;

import com.dev.backend.document.District;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends MongoRepository<District,String> {
    boolean existsByName(String name);
    Optional<District> findByName(String name);
}
