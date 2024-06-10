package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(hidden = true)
public class FeedbackAfterPurchaseDTO {
    private Long purchaseId;
    private String author;
    private String comment;
    private int rating;
}
