package com.dev.backend.repository;

import com.dev.backend.document.Province;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends MongoRepository<Province,String> {
    boolean existsByName(String name);
    Optional<Province> findByName(String name);
}
