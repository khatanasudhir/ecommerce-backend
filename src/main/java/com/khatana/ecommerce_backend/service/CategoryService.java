package com.khatana.ecommerce_backend.service;

import com.khatana.ecommerce_backend.entity.Category;
import com.khatana.ecommerce_backend.repositry.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public Category save(Category category) {
        return categoryRepo.save(category);
    }
}
