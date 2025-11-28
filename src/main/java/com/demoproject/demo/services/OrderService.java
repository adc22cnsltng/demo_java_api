package com.demoproject.demo.services;

import com.demoproject.demo.dtos.OrderDTO;
import com.demoproject.demo.entities.Order;
import com.demoproject.demo.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Conversione entity → DTO
        return new OrderDTO(
                order.getId(),
                order.getUser(),
                order.getCreatedAt(),
                order.getTotal()
        );
    }
}
