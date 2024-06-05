package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    private List<ProductDTO> products;
}