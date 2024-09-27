package com.petproject.ordermanagmentsystem.controllers;

import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id, @RequestParam  int customerId) {
        Order order = orderService.getOrderById(id, customerId);
        return ResponseEntity.ok(order);
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam int customerId){
        List<Order> orderList = orderService.getAllOrders(customerId);
        return ResponseEntity.ok(orderList);
    }





}
