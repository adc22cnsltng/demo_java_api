package com.demoproject.demo.api.controllers;

import com.demoproject.demo.dtos.OrderDTO;
import com.demoproject.demo.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE (POST)
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

    // GET ALL
    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }
}
