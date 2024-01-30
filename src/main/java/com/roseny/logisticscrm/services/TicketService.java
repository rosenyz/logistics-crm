package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.CreateTicketRequest;
import com.roseny.logisticscrm.dtos.response.InfoTicketResponse;
import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.Ticket;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.models.enums.Role;
import com.roseny.logisticscrm.models.enums.StatusTicket;
import com.roseny.logisticscrm.repositories.OrderRepository;
import com.roseny.logisticscrm.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public ResponseEntity<?> findTicketByUUID(UUID ticketUUID, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        User user = userService.findUserByPrincipal(principal);
        Ticket ticket = ticketRepository.findById(ticketUUID).orElse(null);

        if (ticket == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого обращения не существует, создайте новый."); }

        if (!(ticket.getUser().getId().equals(user.getId()) || (user.getRoles().contains(Role.ROLE_ADMIN) || user.getRoles().contains(Role.ROLE_SUPPORT)))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нет доступа.");
        }

        InfoTicketResponse infoTicketResponse = getInfoFromTicket(ticket);

        return ResponseEntity.ok(infoTicketResponse);
    }

    public ResponseEntity<?> findTicketsByStatus(String status) {
        List<Ticket> tickets;

        if (status == null) {
            tickets = ticketRepository.findAll();
        }
        else {
            tickets = ticketRepository.findTicketsByStatus(StatusTicket.valueOf(status));
        }

        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тикетов пока нет.");
        }

        List<InfoTicketResponse> ticketsInfo = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ticketsInfo.add(getInfoFromTicket(ticket));
        }

        return ResponseEntity.ok(ticketsInfo);
    }

    public ResponseEntity<?> takeTicket(UUID ticketUUID, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        Ticket ticket = ticketRepository.findById(ticketUUID).orElse(null);
        if (ticket == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого обращения не существует. Проверьте ID."); }

        if (ticket.getStatus().equals(StatusTicket.STATUS_PROCESS) || ticket.getStatus().equals(StatusTicket.STATUS_CLOSED)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Обращение уже рассматривает другой человек или оно закрыто.");
        }

        ticket.setStaffUser(userService.findUserByPrincipal(principal));
        ticket.setStatus(StatusTicket.STATUS_PROCESS);
        ticket.setDateOfTake(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ResponseEntity.ok(getInfoFromTicket(ticket));
    }

    public ResponseEntity<?> closeTicket(UUID ticketUUID, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"); }

        Ticket ticket = ticketRepository.findById(ticketUUID).orElse(null);
        if (ticket == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого обращения не существует. Проверьте ID."); }

        if (ticket.getStatus().equals(StatusTicket.STATUS_CLOSED)) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Обращение уже закрыто."); }

        if (ticket.getStaffUser() != userService.findUserByPrincipal(principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Обращением занимается другой человек.");
        }

        ticket.setStatus(StatusTicket.STATUS_CLOSED);
        ticket.setDateOfClose(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ResponseEntity.ok(getInfoFromTicket(ticket));
    }

    public Ticket getTicketByUUID(UUID ticketUUID) {
        return ticketRepository.findById(ticketUUID).orElse(null);
    }

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
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
                ticket.getDateOfTake(),
                ticket.getDateOfClose()
        );
    }
}
