package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.order.*;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //Create Order
    @PostMapping
    public void checkout() {
        orderService.checkout();
    }

    //Get Logged-in User Orders
    @GetMapping
    public PageResponseDTO<OrderResponseDTO> getOrders(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return orderService.getUserOrders(page, size);
    }

    // Get Order Details
    @GetMapping("/{id}")
    public OrderDetailResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    //  Cancel Order (USER)
    @PutMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    // ADMIN: Update Status
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/status")
    public void updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        orderService.updateOrderStatus(id, status);
    }

    // ADMIN: Get All Orders
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public PageResponseDTO<OrderResponseDTO> getAllOrders(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return orderService.getAllOrders(page, size);
    }
}