package com.dev.backend.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageObject {
    private String message;
}
