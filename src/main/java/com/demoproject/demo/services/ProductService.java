package com.demoproject.demo.services;

import com.demoproject.demo.dtos.ProductDTO;
import com.demoproject.demo.entities.Product;
import com.demoproject.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        product = productRepository.save(product);
        return toDTO(product);
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock()
        );
    }
}

