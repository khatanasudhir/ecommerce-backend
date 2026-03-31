package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.order.OrderDetailResponseDTO;
import com.khatana.ecommerce_backend.dto.order.OrderResponseDTO;
import com.khatana.ecommerce_backend.dto.order.PageResponseDTO;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public PageResponseDTO<OrderResponseDTO> getOrders(@RequestParam int page,
                                                       @RequestParam int size) {
        return orderService.getUserOrders(page,size);
    }

    @GetMapping("/{id}")
    public OrderDetailResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
