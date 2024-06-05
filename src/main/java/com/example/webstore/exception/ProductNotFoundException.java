package com.example.webstore.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException {
    private final HttpStatus status;

    public ProductNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NO_CONTENT;
    }

    public ProductNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
