package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(hidden = true)
public class BuyRequestDTO {
    private Long userId;
    private Long basketId;
}
