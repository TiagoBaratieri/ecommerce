package com.baratieri.dscommerce.services;

import com.baratieri.dscommerce.dto.ProductDTO;
import com.baratieri.dscommerce.entities.Product;
import com.baratieri.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.orElseThrow(() -> new RuntimeException("Product not found")); // Tratamento para produto não encontrado
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> finAll(Pageable pageable) {
        Page<Product> list = repository.findAll(pageable);
        return list.map(x -> new ProductDTO(x));
    }

    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrL(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity = repository.save(entity);

        return new ProductDTO(entity);
    }
}


