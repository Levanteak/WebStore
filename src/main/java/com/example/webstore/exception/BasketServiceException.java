package com.example.webstore.exception;
import com.example.webstore.exception.*;



public class BasketServiceException extends RuntimeException {
    public BasketServiceException(String message) {
        super(message);
    }

    public BasketServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

