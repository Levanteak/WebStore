package com.example.webstore.controller;

import com.example.webstore.dto.UserDTO;
import com.example.webstore.exception.UserException;
import com.example.webstore.exception.UserNotFoundException;
import com.example.webstore.model.User;
import com.example.webstore.response.UserResponse;
import com.example.webstore.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin("http://localhost:3000")
@Tag(name = "User Controller", description = "API для управления продуктами")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        try {
            UserResponse response = userService.getUserById(id);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            log.error("User not found: " + id, e);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error retrieving user by id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse(1, "Error retrieving user", false, null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        try {
            Map<String, Object> response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (UserException e) {
            log.error("User error: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", 1);
            errorResponse.put("change", false);
            errorResponse.put("description", e.getMessage());
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        } catch (RuntimeException e) {
            log.error("Error creating user", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", 1);
            errorResponse.put("change", false);
            errorResponse.put("description", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (UserException e) {
            log.error("Error retrieving users", e);
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("error", 1, "message", e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", 1, "message", "Unexpected error occurred"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") long userId) {
        try {
            UserResponse response = userService.deleteUser(userId);
            if (response.isFound()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException e) {
            log.error("Error deleting user with id: " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse(1, "Error deleting user", false, null));
        }
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        try {
            UserResponse response = userService.getUserByEmail(email);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            log.error("Error retrieving user by email: " + email, e);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse(1, "Unexpected error occurred", false, null));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserDTO>> getAvailableUsers() {
        try {
            List<UserDTO> users = userService.getAvailableUsers();
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            log.error("Error retrieving available users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}