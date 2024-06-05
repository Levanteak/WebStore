package com.example.webstore.config.security.dto;

public record RegisterRequestDTO (String firstname, String lastname, String login, String email, String password) {
}
