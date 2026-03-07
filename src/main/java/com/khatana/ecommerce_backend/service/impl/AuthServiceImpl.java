package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.auth.RegisterRequestDTO;
import com.khatana.ecommerce_backend.dto.auth.RegisterResponseDTO;
import com.khatana.ecommerce_backend.entity.Role;
import com.khatana.ecommerce_backend.entity.User;
import com.khatana.ecommerce_backend.repositry.UserRepository;
import com.khatana.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setId(savedUser.getId());
        registerResponseDTO.setName(savedUser.getName());
        registerResponseDTO.setEmail(savedUser.getEmail());
        return registerResponseDTO;
    }
}
