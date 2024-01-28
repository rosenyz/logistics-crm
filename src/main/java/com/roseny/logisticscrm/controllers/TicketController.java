package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.CreateTicketRequest;
import com.roseny.logisticscrm.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest, Principal principal) {
        return ticketService.createTicket(createTicketRequest, principal);
    }

    @GetMapping("/{ticket_uuid}")
    public ResponseEntity<?> getAllTicketInfo(@PathVariable(name = "ticket_uuid") UUID ticketUUID, Principal principal) {
        return ticketService.findTicketByUUID(ticketUUID, principal);
    }
}
