package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.MessageSendRequest;
import com.roseny.logisticscrm.models.Message;
import com.roseny.logisticscrm.models.Ticket;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.models.enums.StatusTicket;
import com.roseny.logisticscrm.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final TicketService ticketService;
    private final UserService userService;

    public ResponseEntity<?> sendMessageToTicket(UUID ticketUUID,
                                                 MessageSendRequest messageSendRequest,
                                                 Principal principal) {

        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        Ticket ticket = ticketService.getTicketByUUID(ticketUUID);
        if (ticket == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого обращения не существует."); }

        User user = userService.findUserByPrincipal(principal);

        if (!(user.equals(ticket.getUser()) || user.equals(ticket.getStaffUser()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FORBIDDEN");
        }

        if (ticket.getStatus() == StatusTicket.STATUS_CLOSED) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Писать в закрытое обращение нельзя. Создайте новое.");
        }

        Message message = new Message();

        message.setMessage(messageSendRequest.getMessage());
        message.setUserId(user.getId());
        ticket.getMessages().add(message);

        messageRepository.save(message);
        ticketService.save(ticket);

        return ResponseEntity.ok(message);
    }
}
