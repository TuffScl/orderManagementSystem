package com.petproject.ordermanagmentsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name="notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="message")
    private String message;

    @Column(name="notification_date")
    private Timestamp notificationDate;

    @ManyToOne()
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name="customer_id", referencedColumnName = "id")
    private Customer customer;

}
