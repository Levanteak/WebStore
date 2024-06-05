package com.example.webstore.response;

import com.example.webstore.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Schema(hidden = true)
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Integer count;
    private Category category;
    private UserSummary user;
    private LocalDateTime dateCreate;
    private LocalDateTime delete;

    @Data
    public static class UserSummary {
        private Long userId;
        private String login;
        private String email;
    }
}






