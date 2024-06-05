package com.example.webstore.response;

import lombok.Data;

import java.util.Date;

@Data
public class PurchaseResponseDTO {
    private Long purchaseId;
    private Long userId;
    private Long productId;
    private int count;
    private Date purchaseDate;
}