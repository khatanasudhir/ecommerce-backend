package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.auth.RegisterRequestDTO;
import com.khatana.ecommerce_backend.dto.auth.RegisterResponseDTO;
import com.khatana.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }
    @GetMapping("/test")
    public String test() {
        return "Auth working";
    }
}
