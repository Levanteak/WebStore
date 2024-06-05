package com.example.webstore.service.Impl;

import com.example.webstore.dto.UserDTO;
import com.example.webstore.model.User;
import com.example.webstore.response.UserResponse;

import java.util.List;
import java.util.Map;

public interface ImplUserService {
    Map<String, Object> createUser(User user);
    UserResponse getUserById(long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> getAvailableUsers();
    UserResponse getUserByEmail(String email);
    UserResponse deleteUser(long userId);
}
