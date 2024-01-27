package com.roseny.logisticscrm.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketRequest {
    @NotBlank
    private String header;

    @NotBlank
    private String description;

    private Long orderId;

    @NotNull
    private Boolean withOrder;
}
