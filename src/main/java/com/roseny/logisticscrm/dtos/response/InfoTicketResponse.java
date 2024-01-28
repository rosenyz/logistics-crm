package com.roseny.logisticscrm.dtos.response;

import com.roseny.logisticscrm.models.Message;
import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.enums.StatusTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InfoTicketResponse {
    private UUID id;

    private String header;

    private String description;

    private Order order;

    private List<Message> messages;

    private String username;

    private String usernameStaff;

    private StatusTicket status;

    private LocalDateTime dateOfCreate;

    private LocalDateTime dateOfTake;

    private LocalDateTime dateOfClose;
}
