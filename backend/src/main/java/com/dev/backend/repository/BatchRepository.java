package com.dev.backend.repository;

import com.dev.backend.document.Batch;
import com.dev.backend.document.Employee;
import com.dev.backend.document.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends MongoRepository<Batch,String> {
    List<Batch> findAllByCreator(Employee employee);
    List<Batch> findAllByCreatorAndDestinationStore(Employee employee, Store store);
}
