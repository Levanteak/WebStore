package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class BasketDto {
    private Long basketId;
    private LocalDateTime bought;
    private Integer count;
    private Double total;
    private Long userId;
    private List<ProductDTO> products;
}