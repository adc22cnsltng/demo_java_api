package com.demoproject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demoproject.demo.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
