package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;
import com.khatana.ecommerce_backend.dto.cart.CartResponseDTO;
import com.khatana.ecommerce_backend.dto.cart.UpdateCartItemRequestDTO;
import com.khatana.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public void addToCart(@RequestBody AddToCartRequestDTO request) {
        cartService.addToCart(request);
    }

    @GetMapping
    public CartResponseDTO getCart() {
        return cartService.getCart();
    }

    @PutMapping("/items/{cartItemId}")
    public void updateCartItem(@PathVariable Long cartItemId, @RequestBody UpdateCartItemRequestDTO request) {
        cartService.updateCartItem(cartItemId,request);
    }

    @DeleteMapping("/items/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
    }
}
