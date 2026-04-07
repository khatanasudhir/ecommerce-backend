package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.product.ProductRequestDTO;
import com.khatana.ecommerce_backend.dto.product.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public interface ProductService {
    ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO request);

    Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy);


    void deleteProduct(Long id);

    Page<ProductResponseDTO> searchProducts(
            String name,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size
    );
}
