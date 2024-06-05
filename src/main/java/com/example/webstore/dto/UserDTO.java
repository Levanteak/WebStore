package com.example.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
public class UserDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String role;
    private String login;
    private String email;
    private LocalDateTime dateCreate;
    private List<ProductDTO> products;

}

