package com.roseny.logisticscrm.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "name")
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Category category;
}
