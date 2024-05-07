package com.dev.backend.repository;

import com.dev.backend.document.Employee;
import com.dev.backend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByUser(UserDocument userDocument);

}
