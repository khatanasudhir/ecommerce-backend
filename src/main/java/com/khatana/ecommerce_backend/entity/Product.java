package com.khatana.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String description;
    @Column(nullable = false)
    private BigDecimal price;

    private int stockQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
