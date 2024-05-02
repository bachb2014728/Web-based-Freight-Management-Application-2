package com.dev.backend.document;

import com.dev.backend.web.dto.location.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date date;
    private String phone;
    private String codeId;
    private Address address;
    @DBRef(lazy = true)
    private List<Merchandise> merchandises;
    @DBRef(lazy = true)
    private UserDocument user;

}
