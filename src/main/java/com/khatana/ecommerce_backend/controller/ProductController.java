package com.khatana.ecommerce_backend.controller;

import com.khatana.ecommerce_backend.dto.product.ProductRequestDTO;
import com.khatana.ecommerce_backend.dto.product.ProductResponseDTO;
import com.khatana.ecommerce_backend.payload.ApiResponse;
import com.khatana.ecommerce_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<ProductResponseDTO> createProduct(
            @RequestBody ProductRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Product created successfully",
                productService.createProduct(request)
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        return new ApiResponse<>(
                true,
                "Product updated successfully",
                productService.updateProduct(id, request)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );
    }


    @GetMapping
    public ApiResponse<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return new ApiResponse<>(
                true,
                "Products fetched successfully",
                productService.getAllProducts(page, size, sortBy)
        );
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductResponseDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return new ApiResponse<>(
                true,
                "Filtered products fetched",
                productService.searchProducts(
                        name, categoryId, minPrice, maxPrice, page, size
                )
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDTO> getProductById(@PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Product fetched successfully",
                productService.getProductById(id)
        );
    }
}