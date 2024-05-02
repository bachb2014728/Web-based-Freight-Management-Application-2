package com.dev.backend.repository;

import com.dev.backend.document.Employee;
import com.dev.backend.document.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {
    boolean existsByName(String name);
    Store findByName(String name);
    Store findByEmployee(Employee employee);
    Store findByProvince(String name);
}
