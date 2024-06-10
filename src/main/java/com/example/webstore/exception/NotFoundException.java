package com.example.webstore.exception;

import com.example.webstore.model.Message;

public class NotFoundException extends RuntimeException{
    private String message;


    public  NotFoundException(String message){
        this.message = message;
    }


}
