package com.dev.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(403), accessDeniedException.getMessage());
        problemDetail.setProperty("access_denied_reason", "not_authorized!");
        String timestamp = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        problemDetail.setProperty("timestamp",timestamp);

        // Set the response status
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Set the response content type
        response.setContentType("application/json");

        // Convert the ProblemDetail object to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(problemDetail);

        // Write the JSON to the response
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}