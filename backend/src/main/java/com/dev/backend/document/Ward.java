package com.dev.backend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "wards")
public class Ward {
    @Id
    private String id;
    private String code;
    private String name;
    @DBRef
    private List<UserDocument> users;
    @DBRef
    private List<Router> routers;
}
