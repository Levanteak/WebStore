package com.example.webstore.response;

import com.example.webstore.dto.UserDTO;
import com.example.webstore.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(hidden = true)
public class UserListResponse {
    private int error;
    private String description;
    private boolean success;
    private List<UserDTO> users;

    public UserListResponse(int error, String description, boolean success, List<UserDTO> users) {
        this.error = error;
        this.description = description;
        this.success = success;
        this.users = users;
    }
}