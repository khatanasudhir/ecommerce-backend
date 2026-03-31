package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.order.OrderDetailResponseDTO;
import com.khatana.ecommerce_backend.dto.order.OrderItemDTO;
import com.khatana.ecommerce_backend.dto.order.OrderResponseDTO;
import com.khatana.ecommerce_backend.dto.order.PageResponseDTO;
import com.khatana.ecommerce_backend.entity.*;
import com.khatana.ecommerce_backend.repositry.*;
import com.khatana.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void checkout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        if (items.isEmpty()) {
            throw new RuntimeException("Cart is Empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        order = orderRepository.save(order);

        for (CartItem item: items) {
            Product product = productRepo.findByIdForUpdate(item.getId())
                    .orElseThrow();

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: "
                        + product.getName());
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

            totalPrice = totalPrice.add(item.getProductPrice().
                    multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        cartItemRepository.deleteByCart(cart);
    }

    @Override
    public PageResponseDTO<OrderResponseDTO> getUserOrders(int page, int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

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

        return new PageResponseDTO<>(
                content,
                orders.getNumber(),
                orders.getSize(),
                orders.getTotalElements(),
                orders.getTotalPages(),
                orders.isLast()
        );
    }

    @Override
    public OrderDetailResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(() -> new RuntimeException("Order not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        List<OrderItemDTO> itemDTOS = items.stream().map(item -> new OrderItemDTO(
                item.getProductName(),
                item.getProductPrice(),
                item.getQuantity()
        )).toList();

        return new OrderDetailResponseDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus().name(),
                itemDTOS
        );
    }
}
