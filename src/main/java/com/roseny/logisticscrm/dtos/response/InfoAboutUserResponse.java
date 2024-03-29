package com.roseny.logisticscrm.dtos.response;

import com.roseny.logisticscrm.models.Order;
import com.roseny.logisticscrm.models.enums.StatusTicket;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InfoAboutUserResponse {
    private Long id;

    private String username;

    private String email;

    private Boolean active;

    private String address;

    private List<Order> orders;

    private Map<UUID, StatusTicket> ticketsMap;

    private LocalDateTime dateOfCreate;
}
