package com.demoproject.demo.services;

import com.demoproject.demo.dtos.OrderDTO;
import com.demoproject.demo.dtos.OrderItemDTO;
import com.demoproject.demo.entities.Order;
import com.demoproject.demo.entities.OrderItem;
import com.demoproject.demo.entities.Product;
import com.demoproject.demo.entities.User;
import com.demoproject.demo.repositories.OrderRepository;
import com.demoproject.demo.repositories.ProductRepository;
import com.demoproject.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderDTO> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public OrderDTO create(OrderDTO dto) {

        Order order = new Order();

        // USER
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());

            items.add(item);
        }

        order.setItems(items);

        order = orderRepository.save(order);

        return toDTO(order);
    }

    //entity to dto mapping method
    private OrderDTO toDTO(Order order) {

        List<OrderItemDTO> items = order.getItems().stream()
                .map(i -> new OrderItemDTO(
                        i.getProduct().getId(),
                        i.getQuantity(),
                        i.getProduct().getPrice()
                ))
                .toList();

        return new OrderDTO(
                order.getUser().getName(),  // oppure userId se vuoi
                order.getTotal(),
                items     // ✔ qui ora passi i DTO corretti
        );
    }
}

