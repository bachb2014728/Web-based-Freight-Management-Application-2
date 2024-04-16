package com.dev.backend.repository;

import com.dev.backend.document.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {
    boolean existsById(String id);
}
