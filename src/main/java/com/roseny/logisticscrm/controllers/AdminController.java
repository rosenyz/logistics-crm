package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.AddCategoryRequest;
import com.roseny.logisticscrm.dtos.requests.AddProductRequest;
import com.roseny.logisticscrm.models.Product;
import com.roseny.logisticscrm.services.CategoryService;
import com.roseny.logisticscrm.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/panel")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/add/category")
    public ResponseEntity<?> addCategory(@Valid @RequestBody AddCategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PostMapping("/add/product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

}
