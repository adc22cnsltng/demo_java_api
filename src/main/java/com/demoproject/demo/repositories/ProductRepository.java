package com.demoproject.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demoproject.demo.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
