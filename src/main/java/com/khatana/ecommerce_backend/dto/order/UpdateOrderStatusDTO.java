package com.khatana.ecommerce_backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class UpdateOrderStatusDTO {
    private String status;
}
