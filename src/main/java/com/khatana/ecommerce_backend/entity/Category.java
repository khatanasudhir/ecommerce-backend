package com.khatana.ecommerce_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private String name;

    private String description;
}
