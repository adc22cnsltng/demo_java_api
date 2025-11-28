package com.demoproject.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long userId;
    private String user_name;
    private Double total;
    private List<OrderItemDTO> items;

    public OrderDTO(String user_name, Double total, List<OrderItemDTO> items){
        this.user_name = user_name;
        this.total = total;
        this.items = items;
    }
}
