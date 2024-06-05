package com.example.webstore.response;

import com.example.webstore.dto.ProductDTO;
import com.example.webstore.model.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BasketResponseDTO {
    private Long basketId;
    private Long userId;
    private Boolean bought;
    private Integer count;
    private Double total;
    private List<ProductDTO> products;
}