package com.example.webstore.exception;

public class BasketNotFoundException extends BasketServiceException {
    public BasketNotFoundException(String message) {
        super(message);
    }
}