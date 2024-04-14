package com.dev.backend.repository;

import com.dev.backend.document.Router;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouterRepository extends MongoRepository<Router,String> {
    Optional<Router> getRouterById(String id);
}
