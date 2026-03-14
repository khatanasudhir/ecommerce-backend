package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;
import com.khatana.ecommerce_backend.entity.Cart;
import com.khatana.ecommerce_backend.entity.CartItem;
import com.khatana.ecommerce_backend.entity.Product;
import com.khatana.ecommerce_backend.entity.User;
import com.khatana.ecommerce_backend.repositry.CartItemRepository;
import com.khatana.ecommerce_backend.repositry.CartRepository;
import com.khatana.ecommerce_backend.repositry.ProductRepo;
import com.khatana.ecommerce_backend.repositry.UserRepository;
import com.khatana.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    @Override
    public void addToCart(AddToCartRequestDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart,product);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();

            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cartItem.setProduct(product);

            cartItem.setProductPrice(product.getPrice());

            cartItemRepository.save(cartItem);
        }

    }
}
