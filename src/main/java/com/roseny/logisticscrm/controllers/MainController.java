package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {
    private final ProductService productService;

    @GetMapping("/catalog")
    public ResponseEntity<?> getProducts(@RequestParam(name = "category_id", required = false) Long categoryId) {
        if (categoryId == null) { return productService.allProducts(); }

        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/products/{product_id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "product_id") Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/products")
        public ResponseEntity<?> allProducts() {
        return productService.allProducts();
    }
}
