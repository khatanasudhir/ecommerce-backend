package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.order.*;
import com.khatana.ecommerce_backend.entity.*;
import com.khatana.ecommerce_backend.exception.BadRequestFoundException;
import com.khatana.ecommerce_backend.exception.ResourceNotFoundException;
import com.khatana.ecommerce_backend.repositry.*;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepo productRepo;

    // ✅ CHECKOUT
    @Override
    public void checkout() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        if (items.isEmpty()) {
            throw new BadRequestFoundException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        order = orderRepository.save(order);

        for (CartItem item : items) {

            // ✅ FIXED BUG HERE
            Product product = productRepo.findByIdForUpdate(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BadRequestFoundException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepo.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductPrice(item.getProductPrice());

            orderItemRepository.save(orderItem);

            totalPrice = totalPrice.add(
                    item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        cartItemRepository.deleteByCart(cart);
    }

    // ✅ USER ORDERS
    @Override
    public PageResponseDTO<OrderResponseDTO> getUserOrders(int page, int size) {

        User user = getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = orderRepository.findByUser(user, pageable);

        List<OrderResponseDTO> content = orders.getContent()
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getStatus().name()
                ))
                .toList();

        return buildPageResponse(orders, content);
    }

    // ✅ ORDER DETAILS
    @Override
    public OrderDetailResponseDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = getCurrentUser();

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestFoundException("Unauthorized access");
        }

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        List<OrderItemDTO> itemDTOS = items.stream()
                .map(item -> new OrderItemDTO(
                        item.getProductName(),
                        item.getProductPrice(),
                        item.getQuantity()
                ))
                .toList();

        return new OrderDetailResponseDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus().name(),
                itemDTOS
        );
    }

    // ✅ UPDATE STATUS (ADMIN)
    @Override
    public void updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        try {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestFoundException("Invalid order status");
        }

        orderRepository.save(order);
    }

    // ✅ CANCEL ORDER (USER)
    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = getCurrentUser();

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestFoundException("Unauthorized access");
        }

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED) {
            throw new BadRequestFoundException("Cannot cancel this order");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // ✅ ADMIN: ALL ORDERS
    @Override
    public PageResponseDTO<OrderResponseDTO> getAllOrders(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orders = orderRepository.findAll(pageable);

        List<OrderResponseDTO> content = orders.getContent()
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getTotalPrice(),
                        order.getStatus().name()
                ))
                .toList();

        return buildPageResponse(orders, content);
    }

    // 🔧 COMMON METHODS

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    private <T> PageResponseDTO<T> buildPageResponse(Page<?> page, List<T> content) {
        return new PageResponseDTO<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}