package com.example.webstore.controller;


import com.example.webstore.config.dto.LoginRequestDTO;
import com.example.webstore.config.dto.RegisterRequestDTO;
import com.example.webstore.config.dto.ResponseDTO;
import com.example.webstore.config.infra.security.TokenService;
import com.example.webstore.model.User;
import com.example.webstore.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@Tag(name = "AuthController Controller", description = "API для Авторизации")
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByLogin(body.login()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getFirstname(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDTO("Email already registered", null));
        } else {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setFirstname(body.firstname());
            newUser.setLastname(body.lastname());
            newUser.setLogin(body.login());
            newUser.setRole(body.role());
            newUser.setDate_create(LocalDateTime.now());

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getFirstname(), token));
        }
    }
}
