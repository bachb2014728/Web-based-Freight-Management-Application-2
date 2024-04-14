package com.dev.backend.repository;

import com.dev.backend.document.Ward;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends MongoRepository<Ward, String> {
    boolean existsByName(String name);
}
