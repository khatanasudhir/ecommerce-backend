package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.entity.Category;
import com.khatana.ecommerce_backend.entity.User;
import com.khatana.ecommerce_backend.service.CategoryService;
import com.khatana.ecommerce_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {
    private final CategoryService categoryService;
    private  final UserService userService;

    @PostMapping("/category")
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PostMapping("/user-test")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
