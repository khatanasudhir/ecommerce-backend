package com.khatana.ecommerce_backend.repositry;

import com.khatana.ecommerce_backend.entity.Cart;
import com.khatana.ecommerce_backend.entity.CartItem;
import com.khatana.ecommerce_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
