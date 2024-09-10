package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.utils.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByStatus(OrderStatus status);
    Optional<List<Order>> findByOwner(Customer customer);

    Optional<Order> findByOwnerAndId(Customer customer, int id);


    Optional<Order> findByStatusAndOwner(OrderStatus status, Customer customer);

    Optional<List<Order>> findByStatusNotAndOwner(OrderStatus status, Customer customer);
}
