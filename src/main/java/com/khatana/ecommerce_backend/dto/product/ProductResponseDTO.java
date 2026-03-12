package com.khatana.ecommerce_backend.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private String categoryName;
}
