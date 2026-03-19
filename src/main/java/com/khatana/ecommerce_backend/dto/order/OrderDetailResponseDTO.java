package com.khatana.ecommerce_backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
    private Long orderId;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemDTO> items;
}
