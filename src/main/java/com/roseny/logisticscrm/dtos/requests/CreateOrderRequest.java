package com.roseny.logisticscrm.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotBlank
    private String customerContact;

    private String addressForDelivery;

    private String commentary;

    private List<Long> productsId;
}
