package com.dev.backend.exception;

import com.dev.backend.error.ServerErrorException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ServerErrorException.class)
    public ProblemDetail handlerRuntimeException(ServerErrorException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),ex.getMessage());
        problemDetail.setDetail(ex.getMessage());
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        problemDetail.setProperty("timestamp", timestamp);
        return problemDetail;
    }
}
