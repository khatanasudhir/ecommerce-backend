package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.order.*;

public interface OrderService {

    void checkout();

    PageResponseDTO<OrderResponseDTO> getUserOrders(int page, int size);

    OrderDetailResponseDTO getOrderById(Long orderId);

    void updateOrderStatus(Long orderId, String status);

    void cancelOrder(Long orderId);

    PageResponseDTO<OrderResponseDTO> getAllOrders(int page, int size);
}