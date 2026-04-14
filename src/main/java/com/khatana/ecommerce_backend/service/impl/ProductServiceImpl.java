package com.khatana.ecommerce_backend.service.impl;

import com.khatana.ecommerce_backend.dto.product.ProductRequestDTO;
import com.khatana.ecommerce_backend.dto.product.ProductResponseDTO;
import com.khatana.ecommerce_backend.entity.Category;
import com.khatana.ecommerce_backend.entity.Product;
import com.khatana.ecommerce_backend.exception.ResourceNotFoundException;
import com.khatana.ecommerce_backend.repositry.CategoryRepo;
import com.khatana.ecommerce_backend.repositry.ProductRepo;
import com.khatana.ecommerce_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;


    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);

        Product savedProduct = productRepo.save(product);

        return mapToResponse(savedProduct);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy) {

        List<String> allowedSortFields = List.of("id", "name", "price");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Product> productPage = productRepo.findAll(pageable);

        return productPage.map(this::mapToResponse);
    }


    @Override
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);

        Product updatedProduct = productRepo.save(product);

        return mapToResponse(updatedProduct);
    }


    private ProductResponseDTO mapToResponse(Product product) {

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategoryName(product.getCategory().getName());

        return response;
    }
}