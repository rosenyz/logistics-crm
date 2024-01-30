package com.roseny.logisticscrm.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "date_of_send")
    private LocalDateTime dateOfSend;

    @PrePersist
    public void init() {
        this.dateOfSend = LocalDateTime.now();
    }
}
