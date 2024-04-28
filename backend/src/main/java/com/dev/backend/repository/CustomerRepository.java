package com.dev.backend.repository;

import com.dev.backend.document.Customer;
import com.dev.backend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByUser(UserDocument userDocument);
}
