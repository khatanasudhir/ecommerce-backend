package com.khatana.ecommerce_backend.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
