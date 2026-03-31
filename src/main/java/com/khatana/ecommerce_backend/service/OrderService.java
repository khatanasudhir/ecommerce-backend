package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.order.OrderDetailResponseDTO;
import com.khatana.ecommerce_backend.dto.order.OrderResponseDTO;
import com.khatana.ecommerce_backend.dto.order.PageResponseDTO;

public interface OrderService {

    void checkout();

    PageResponseDTO<OrderResponseDTO> getUserOrders(int page, int size);

    OrderDetailResponseDTO getOrderById(Long orderId);

    void updateOrderStatus(Long orderId,String status);
}
