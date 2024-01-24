package com.roseny.logisticscrm.models;

import com.roseny.logisticscrm.models.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    void init() {
        this.balance = 0.0;
        this.address = "Не указан";
        this.dateOfCreate = LocalDateTime.now();
    }
}