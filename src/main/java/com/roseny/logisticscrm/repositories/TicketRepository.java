package com.roseny.logisticscrm.repositories;

import com.roseny.logisticscrm.models.Ticket;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.models.enums.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findTicketsByStatus(StatusTicket status);
    List<Ticket> findTicketsByUser(User user);
}
