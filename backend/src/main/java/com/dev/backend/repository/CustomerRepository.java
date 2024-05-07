package com.dev.backend.repository;

import com.dev.backend.document.Customer;
import com.dev.backend.document.Province;
import com.dev.backend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findByUser(UserDocument userDocument);
    List<Customer> findAllByAddress_Province(Province address_province);
}
