package com.movieinventory;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private String message;
}
