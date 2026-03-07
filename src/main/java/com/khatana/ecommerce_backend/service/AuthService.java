package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.auth.RegisterRequestDTO;
import com.khatana.ecommerce_backend.dto.auth.RegisterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;


public interface AuthService {
    RegisterResponseDTO register(@Valid @RequestBody RegisterRequestDTO request);
}
