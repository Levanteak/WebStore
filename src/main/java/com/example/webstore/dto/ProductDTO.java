package com.example.webstore.dto;

import com.example.webstore.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Integer count;
    private Category category;


}