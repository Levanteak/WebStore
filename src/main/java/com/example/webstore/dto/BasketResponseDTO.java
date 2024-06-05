package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(hidden = true)
public class BasketResponseDTO {
    private Long basketId;
    private Long userId;
    private LocalDateTime bought;
    private Integer count;
    private Double total;
    private List<ProductDTO> products;
}