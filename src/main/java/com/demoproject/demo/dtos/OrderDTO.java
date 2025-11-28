package com.demoproject.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UserDTO user;
    private LocalDateTime createdAt;
    private Double total;
    private List<OrderItemDTO> items;
}
