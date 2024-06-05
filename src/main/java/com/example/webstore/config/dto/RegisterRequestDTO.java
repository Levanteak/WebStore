package com.example.webstore.config.dto;

public record RegisterRequestDTO (String firstname, String lastname, String login, String email, String password, String role) {
}
