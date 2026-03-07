package com.khatana.ecommerce_backend.dto.auth;

import lombok.Data;

@Data
public class RegisterResponseDTO {
    private Long id;
    private String name;
    private String email;
}
