package com.dev.backend.rest.dto;

import lombok.Data;

@Data
public class LogInRequest {
    private String email;
    private String password;
}
