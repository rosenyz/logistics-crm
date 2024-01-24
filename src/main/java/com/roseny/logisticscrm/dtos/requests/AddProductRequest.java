package com.roseny.logisticscrm.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductRequest {
    @NotBlank
    private String name;
    @NotNull
    private Double price;
    @NotBlank
    private String description;
    @NotNull
    private Integer quantity;
    @NotNull
    private Long category_id;
}
