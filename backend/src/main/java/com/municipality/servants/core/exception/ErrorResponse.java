package com.municipality.servants.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = new HashMap<>();
    }

    public ErrorResponse(String message, Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.errors = errors;
    }
}
