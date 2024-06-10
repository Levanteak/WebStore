package com.example.webstore.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewsDTO {
    private Long feedbackId;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long productId;
    private String productName;
    private String productCategory;
    private Long productPrice;
    private String comment;
    private int rating;
    private Date feedbackDate;
    private String author;
}
