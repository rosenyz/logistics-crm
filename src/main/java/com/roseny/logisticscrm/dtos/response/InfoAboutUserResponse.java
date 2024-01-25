package com.roseny.logisticscrm.dtos.response;

import com.roseny.logisticscrm.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InfoAboutUserResponse {
    private Long id;

    private String username;

    private String email;

    private Boolean active;

    private String address;

    private List<Order> orders;

    private LocalDateTime dateOfCreate;
}
