package com.khatana.ecommerce_backend.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private Long categoryId;
}
