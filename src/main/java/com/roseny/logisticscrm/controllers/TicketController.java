package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.CreateTicketRequest;
import com.roseny.logisticscrm.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest, Principal principal) {
        return ticketService.createTicket(createTicketRequest, principal);
    }

}
