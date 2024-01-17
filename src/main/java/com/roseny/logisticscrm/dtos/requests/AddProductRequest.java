package com.roseny.logisticscrm.dtos.requests;

import lombok.Data;

@Data
public class AddProductRequest {
    private String name;

    private Double price;

    private String description;

    private Integer quantity;

    private Long category_id;
}
