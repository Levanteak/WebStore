package com.example.webstore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class MessageDTO {
    private Long messageId;
    private Long senderId;
    private Long recipientId;
    private String text;
    private LocalDateTime timestamp;

    @Data
    @Getter
    @Setter
    public static class UserSummary {
        private Long userId;
        private String login;
        private String email;
    }

    private UserSummary sender;
    private UserSummary recipient;
}