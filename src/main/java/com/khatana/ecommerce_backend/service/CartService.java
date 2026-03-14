package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;

public interface CartService {
    void addToCart(AddToCartRequestDTO request);
}
