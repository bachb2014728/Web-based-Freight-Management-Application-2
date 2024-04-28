package com.dev.backend.repository;

import com.dev.backend.document.Merchandise;
import com.dev.backend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchandiseRepository extends MongoRepository<Merchandise, String> {
    boolean existsById(String id);
    List<Merchandise> findAllByCode(UserDocument userDocument);
}
