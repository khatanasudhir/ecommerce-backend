package com.khatana.ecommerce_backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
}
