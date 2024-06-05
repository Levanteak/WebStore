package com.example.webstore.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(hidden = true)
public class ProductRequest {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Integer count;
    private Long categoryId;
    private Long userId;


}
