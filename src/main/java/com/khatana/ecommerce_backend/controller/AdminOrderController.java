package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.order.UpdateOrderStatusDTO;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public void updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusDTO request) {
        orderService.updateOrderStatus(id,request.getStatus());
    }
}
