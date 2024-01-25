package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.AddCategoryRequest;
import com.roseny.logisticscrm.dtos.requests.AddProductRequest;
import com.roseny.logisticscrm.models.Product;
import com.roseny.logisticscrm.services.CategoryService;
import com.roseny.logisticscrm.services.ProductService;
import com.roseny.logisticscrm.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/panel")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/add/category")
    public ResponseEntity<?> addCategory(@Valid @RequestBody AddCategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PostMapping("/add/product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @GetMapping("/product/{product_id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "product_id") Long productId) throws Exception {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getInfoAboutUser(@PathVariable(name = "user_id") Long userId) {
        return userService.getInfoAboutUserById(userId);
    }
}
