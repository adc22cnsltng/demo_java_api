package com.demoproject.demo.api.controllers;

import com.demoproject.demo.entities.Order;
import com.demoproject.demo.entities.OrderItem;
import com.demoproject.demo.entities.Product;
import com.demoproject.demo.entities.User;
import com.demoproject.demo.repositories.OrderRepository;
import com.demoproject.demo.repositories.ProductRepository;
import com.demoproject.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderRepository orderRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // CREATE ORDER
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderInput) {

        // Caricare l'utente
        User user = userRepository.findById(orderInput.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        orderInput.setUser(user);

        // Inizializza la lista items se null
        if (orderInput.getItems() == null) {
            orderInput.setItems(new ArrayList<>());
        }

        // Ricollegare i Products veri (presi dal DB)
        for (OrderItem item : orderInput.getItems()) {

            Product p = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            item.setProduct(p);
            item.setOrder(orderInput); // IMPORTANTISSIMO
        }

        Order saved = orderRepository.save(orderInput);
        return ResponseEntity.ok(saved);
    }

    // GET ALL
    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOne(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderInput) {

        return orderRepository.findById(id).map(existing -> {

            existing.setUser(orderInput.getUser()); // opzionale
            existing.setTotal(orderInput.getTotal());

            // Pulisco gli items esistenti
            existing.getItems().clear();

            // Ricreo gli items
            for (OrderItem item : orderInput.getItems()) {

                Product p = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                item.setOrder(existing);
                item.setProduct(p);

                existing.getItems().add(item);
            }

            return ResponseEntity.ok(orderRepository.save(existing));

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id))
            return ResponseEntity.notFound().build();

        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

