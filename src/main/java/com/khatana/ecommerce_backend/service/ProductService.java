package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.dto.product.ProductRequestDTO;
import com.khatana.ecommerce_backend.dto.product.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy);

    void deleteProduct(Long id);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);

    Page<ProductResponseDTO> searchProducts(
            String name,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size
    );
}