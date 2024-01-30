package com.roseny.logisticscrm.controllers;

import com.roseny.logisticscrm.dtos.requests.MessageSendRequest;
import com.roseny.logisticscrm.services.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessageToTicket(@RequestParam(name = "ticket_uuid")UUID ticketUUID,
                                                 @Valid @RequestBody MessageSendRequest messageSendRequest,
                                                 Principal principal) {
        return messageService.sendMessageToTicket(ticketUUID, messageSendRequest, principal);
    }
}
