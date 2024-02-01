package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.services.OrderService;
import com.roseny.logisticscrm.services.TicketService;
import com.roseny.logisticscrm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final TicketService ticketService;

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getInfoAboutUser(@PathVariable(name = "user_id") Long userId) {
        return userService.getInfoAboutUserById(userId);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }
        return userService.getInfoAboutUserById(userService.findUserByPrincipal(principal).getId());
    }

    @GetMapping("/profile/orders")
    public ResponseEntity<?> getOrders(Principal principal) {
        return orderService.getUserOrders(principal);
    }

    @GetMapping("/profile/tickets")
    public ResponseEntity<?> getTickets(
            @RequestParam(name = "ticket_uuid", required = false) UUID ticketUUID,
            Principal principal) {
        return (ticketUUID == null)
                ? ticketService.findAllTickets(principal) : ticketService.findTicketByUUID(ticketUUID, principal);
    }
}
