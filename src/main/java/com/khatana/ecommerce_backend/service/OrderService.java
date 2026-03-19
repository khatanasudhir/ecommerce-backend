package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.order.OrderDetailResponseDTO;
import com.khatana.ecommerce_backend.dto.order.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    void checkout();

    List<OrderResponseDTO> getUserOrders();

    OrderDetailResponseDTO getOrderById(Long orderId);
}
