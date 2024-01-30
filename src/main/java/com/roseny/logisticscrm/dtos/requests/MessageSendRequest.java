package com.roseny.logisticscrm.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageSendRequest {
    @NotBlank
    private String message;
}
