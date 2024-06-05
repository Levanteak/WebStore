package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(hidden = true)
public class PurchaseResponseDTO {
    private Long purchaseId;
    private Long userId;
    private Long productId;
    private int count;
    private Date purchaseDate;
}