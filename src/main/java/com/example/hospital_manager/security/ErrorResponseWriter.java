package com.example.hospital_manager.security;

import com.example.hospital_manager.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ErrorResponseWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    public void write(HttpServletResponse response, int status, String message, String path) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            status,
            status == 401 ? "Unauthorized" : "Forbidden",
            message,
            path
        );

        mapper.findAndRegisterModules().writeValue(response.getOutputStream(), error);
    }
}