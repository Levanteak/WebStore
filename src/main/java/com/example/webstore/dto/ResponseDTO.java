package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
public class ResponseDTO {
    private String name;
    private String message;

    public ResponseDTO(String name) {
        this.name = name;
        this.message = message;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}