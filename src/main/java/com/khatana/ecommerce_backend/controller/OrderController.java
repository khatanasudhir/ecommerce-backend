package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.order.OrderDetailResponseDTO;
import com.khatana.ecommerce_backend.dto.order.OrderResponseDTO;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void checkout() {
        orderService.checkout();
    }

    @GetMapping
    public List<OrderResponseDTO> getOrders() {
        return orderService.getUserOrders();
    }

    @GetMapping("/{id}")
    public OrderDetailResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
