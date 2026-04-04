package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.product.ProductRequestDTO;
import com.khatana.ecommerce_backend.dto.product.ProductResponseDTO;
import com.khatana.ecommerce_backend.entity.Category;
import com.khatana.ecommerce_backend.entity.Product;
import com.khatana.ecommerce_backend.exception.ResourceNotFoundException;
import com.khatana.ecommerce_backend.repositry.CategoryRepo;
import com.khatana.ecommerce_backend.repositry.ProductRepo;
import com.khatana.ecommerce_backend.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(category);
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        Product savedProduct = productRepo.save(product);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(savedProduct.getId());
        response.setPrice(savedProduct.getPrice());
        response.setName(savedProduct.getName());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setCategoryName(category.getName());
        return response;
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Product> productPage = productRepo.findAll(pageable);

        return productPage.map(this::mapToResponse);
    }

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setCategoryName(product.getCategory().getName());
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        return response;
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        productRepo.delete(product);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> searchProducts(
            String name,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepo.searchProducts(
                name,
                categoryId,
                minPrice,
                maxPrice,
                pageable
        );

        return productPage.map(this::mapToResponse);
    }
}
