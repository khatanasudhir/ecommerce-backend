package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;
import com.khatana.ecommerce_backend.dto.cart.CartResponseDTO;
import com.khatana.ecommerce_backend.dto.cart.UpdateCartItemRequestDTO;

public interface CartService {
    void addToCart(AddToCartRequestDTO request);

    CartResponseDTO getCart();

    void updateCartItem(Long cartItemId, UpdateCartItemRequestDTO request);

    void removeCartItem(Long cartItemId);
}
