package com.khatana.ecommerce_backend.dto.product;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private Long categoryId;
}
