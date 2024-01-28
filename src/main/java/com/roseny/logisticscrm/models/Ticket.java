package com.roseny.logisticscrm.models;

import com.roseny.logisticscrm.models.enums.StatusTicket;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "header")
    private String header;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Order order;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "ticketUuid")
    private List<Message> messages;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User staffUser;

    @Enumerated(EnumType.ORDINAL)
    private StatusTicket status;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @Column(name = "date_of_take")
    private LocalDateTime dateOfTake;

    @Column(name = "date_of_close")
    private LocalDateTime dateOfClose;

    @PrePersist
    public void init() {
        this.status = StatusTicket.STATUS_OPEN;
        this.dateOfCreate = LocalDateTime.now();
        this.staffUser = null;
        this.dateOfTake = null;
        this.dateOfClose = null;
    }
}
