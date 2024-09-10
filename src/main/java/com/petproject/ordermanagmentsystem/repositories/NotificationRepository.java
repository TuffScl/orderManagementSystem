package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.models.Notification;
import com.petproject.ordermanagmentsystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findByIdAndCustomerAndOrder(int id, Customer customer, Order order);
    Optional<List<Notification>> findAllByCustomerAndOrder(Customer customer, Order order);

    Optional<List<Notification>> findAllByCustomer(Customer customer);


}
