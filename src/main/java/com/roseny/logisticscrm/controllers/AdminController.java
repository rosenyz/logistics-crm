package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.AddCategoryRequest;
import com.roseny.logisticscrm.dtos.requests.AddProductRequest;
import com.roseny.logisticscrm.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/panel")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final TicketService ticketService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/add/category")
    public ResponseEntity<?> addCategory(@Valid @RequestBody AddCategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PostMapping("/add/product")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "user_id") Long userId) {
        return userService.getInfoAboutUserById(userId);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
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

    @GetMapping("/tickets")
    public ResponseEntity<?> getAllTicketsByStatus(@RequestParam(name = "status", required = false) String status) {
        return ticketService.findTicketsByStatus(status);
    }

    @GetMapping("/ticket/{ticket_uuid}")
    public ResponseEntity<?> takeTicket(@RequestParam(name = "action", required = false) String action, @PathVariable(name = "ticket_uuid") UUID ticketUUID, Principal principal) {
        if (action.equals("take"))
            return ticketService.takeTicket(ticketUUID, principal);

        if (action.equals("close")) {
            return ticketService.closeTicket(ticketUUID, principal);
        }

        return ResponseEntity.ok("dsa");
    }
}
