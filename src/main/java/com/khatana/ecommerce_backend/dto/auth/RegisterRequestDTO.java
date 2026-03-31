package com.khatana.ecommerce_backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String role;
}
