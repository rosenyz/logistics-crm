package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.AddCategoryRequest;
import com.roseny.logisticscrm.models.Category;
import com.roseny.logisticscrm.repositories.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> addCategory(@Valid AddCategoryRequest categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

        return ResponseEntity.ok("Category %s successful added".formatted(category.getName()));
    }
}
