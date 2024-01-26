package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.services.OrderService;
import com.roseny.logisticscrm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getInfoAboutUser(@PathVariable(name = "user_id") Long userId) {
        return userService.getInfoAboutUserById(userId);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }
        return userService.getInfoAboutUserById(userService.findUserByPrincipal(principal).getId());
    }

    @GetMapping("/profile/orders")
    public ResponseEntity<?> getUserOrders(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }
        return orderService.getUserOrders(principal);
    }
}
