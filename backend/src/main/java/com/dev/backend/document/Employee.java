package com.dev.backend.document;

import com.dev.backend.web.dto.location.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private String identifier; // Căn cước công dân
    private String phone;
    private List<Employee> employees;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @DBRef
    private UserDocument user;
}
