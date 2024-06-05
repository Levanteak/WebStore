package com.example.webstore.response;


import com.example.webstore.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.json.JSONObject;

@Data
@Schema(hidden = true)
public class UserResponse {
    private int error;
    private String description;
    private boolean found;
    private User user;

    public UserResponse(int error, String description, boolean found, User user) {
        this.error = error;
        this.description = description;
        this.found = found;
        this.user = user;
    }

    public UserResponse(User user) {
        this.user = user;
    }
}
