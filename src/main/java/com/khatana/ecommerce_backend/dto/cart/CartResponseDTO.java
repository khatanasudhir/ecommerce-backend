package com.khatana.ecommerce_backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartResponseDTO {

    private List<CartItemResponseDTO> items;
    private BigDecimal totalPrice;

}
