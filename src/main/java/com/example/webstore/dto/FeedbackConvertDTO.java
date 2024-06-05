package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(hidden = true)
public class FeedbackConvertDTO {
    private Long feedbackId;
    private Long purchaseId;
    private ProductSummary product;
    private UserSummary user;
    private String author;
    private String comment;
    private int rating;
    private Date feedbackDate;

    @Data
    public static class UserSummary {
        private Long userId;
        private String login;
        private String email;
    }

    @Data
    public static class ProductSummary {
        private Long productId;
        private String name;
        private String description;
    }
}
