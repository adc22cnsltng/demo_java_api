package com.demoproject.demo.api.controllers;

import com.demoproject.demo.dtos.ProductDTO;
import com.demoproject.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO dto) {
        return productService.create(dto);
    }
}

