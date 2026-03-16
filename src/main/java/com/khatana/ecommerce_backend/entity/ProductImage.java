package com.khatana.ecommerce_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_images")
public class ProductImage extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String imageUrl;
}
