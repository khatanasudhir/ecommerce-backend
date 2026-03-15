package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.cart.AddToCartRequestDTO;
import com.khatana.ecommerce_backend.dto.cart.CartItemResponseDTO;
import com.khatana.ecommerce_backend.dto.cart.CartResponseDTO;
import com.khatana.ecommerce_backend.dto.cart.UpdateCartItemRequestDTO;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;
    @Override
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        List<CartItemResponseDTO> items = cartItems.stream()
                .map(item -> new CartItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProductPrice(),
                        item.getQuantity()
                ))
                .toList();

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProductPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponseDTO(items, totalPrice);
    }

    @Override
    public void updateCartItem(Long cartItemId, UpdateCartItemRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        assert user != null;
        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized cart access");
        }

        if (request.getQuantity() <= 0) {
            cartItemRepository.delete(cartItem);
            return;
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

    }

    @Override
    public void removeCartItem(Long cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart Item not found"));

        assert user != null;
        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized cart access");
        }

        cartItemRepository.delete(cartItem);
    }
}
