package com.dev.backend.web.dto;

import com.dev.backend.document.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class CreateEmployee {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String ward;
    @NotNull
    private String district;
    @NotNull
    private String province;
    private String identifier;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String gender;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Role role;
    private boolean isEnabled;
}
