package com.khatana.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carts")
@Data
public class Cart extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
