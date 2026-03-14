package com.khatana.ecommerce_backend.dto.cart;

import lombok.Data;

@Data
public class AddToCartRequestDTO {
    private Long productId;
    private Integer quantity;
}
