package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.CreateOrderRequest;
import com.roseny.logisticscrm.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest, Principal principal) {
        return orderService.createOrder(createOrderRequest, principal);
    }
}
