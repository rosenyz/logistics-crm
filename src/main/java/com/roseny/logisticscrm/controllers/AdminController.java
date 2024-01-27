package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.AddCategoryRequest;
import com.roseny.logisticscrm.dtos.requests.AddProductRequest;
import com.roseny.logisticscrm.services.CategoryService;
import com.roseny.logisticscrm.services.OrderService;
import com.roseny.logisticscrm.services.ProductService;
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
    private final OrderService orderService;

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

    @GetMapping("/orders/all")
    public ResponseEntity<?> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderById(@PathVariable(name = "order_id") Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
