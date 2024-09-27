package com.petproject.ordermanagmentsystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private long phone;

    @Column(name="address")
    private String address;

    @Column(name="balance")
    private int balance;

    @OneToMany(mappedBy = "owner")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Notification> notifications;
}
