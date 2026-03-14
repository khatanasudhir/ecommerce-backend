package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;
import com.khatana.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public void addToCart(@RequestBody AddToCartRequestDTO request) {
        cartService.addToCart(request);
    }
}
