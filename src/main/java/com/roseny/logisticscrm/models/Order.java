package com.roseny.logisticscrm.models;

import com.roseny.logisticscrm.models.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_contact")
    private String customerContact;

    @Column(name = "address_for_delivery", columnDefinition = "text")
    private String addressForDelivery;

    @Column(name = "commentary", columnDefinition = "text")
    private String commentary;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Product> products; // may be null, cause customer can order something thats not in catalog ( place url in commentary, for example )

    @Enumerated(EnumType.ORDINAL)
    private StatusOrder status;

    @Column(name = "products_price")
    private Double productsPrice;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @PrePersist
    public void init() {
        this.dateOfCreate = LocalDateTime.now();
        this.status = StatusOrder.STATUS_ORDERED;
    }
}
