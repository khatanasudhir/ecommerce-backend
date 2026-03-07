package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.entity.User;
import com.khatana.ecommerce_backend.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
