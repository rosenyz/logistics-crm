package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.CreateTicketRequest;
import com.roseny.logisticscrm.dtos.response.InfoTicketResponse;
import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.Ticket;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.repositories.OrderRepository;
import com.roseny.logisticscrm.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    public ResponseEntity<?> createTicket(CreateTicketRequest createTicketRequest, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        User user = userService.findUserByPrincipal(principal);
        Ticket ticket = new Ticket();

        if (createTicketRequest.getWithOrder()) {
            Order order = orderRepository.findById(createTicketRequest.getOrderId()).orElse(null);

            if (order == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Заказа под номером %d не существует.".formatted(createTicketRequest.getOrderId()));
            }
            ticket.setOrder(order);
        }

        ticket.setHeader(createTicketRequest.getHeader());
        ticket.setDescription(createTicketRequest.getDescription());
        ticket.setUser(user);
        ticket.setStaffUser(null);

        user.getTickets().add(ticket);
        ticketRepository.save(ticket);
        userService.save(user);

        return ResponseEntity.ok(getInfoFromTicket(ticket));
    }

    public InfoTicketResponse getInfoFromTicket(Ticket ticket) {

        return new InfoTicketResponse(
                ticket.getId(),
                ticket.getHeader(),
                ticket.getDescription(),
                ticket.getOrder(),
                ticket.getMessages(),
                ticket.getUser().getUsername(),
                (ticket.getStaffUser() == null) ? null : ticket.getStaffUser().getUsername(),
                ticket.getStatus(),
                ticket.getDateOfCreate(),
                ticket.getDateOfClose()
        );
    }
}
