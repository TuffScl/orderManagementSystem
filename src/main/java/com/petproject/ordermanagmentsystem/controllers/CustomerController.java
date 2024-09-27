package com.petproject.ordermanagmentsystem.controllers;

import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id){
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomerById(id);
    }

    @PostMapping("/new")
    public void createCustomer(@RequestBody Customer customer){
        customerService.createNewCustomer(customer);
    }

    @PutMapping("/{id}")
    public void changeCustomer(@PathVariable int id, Customer customer){
        customerService.updateCustomerById(id, customer);
    }

    @PutMapping("/{id}/balance")
    public void changeBalance(@PathVariable int id, int amount){
        customerService.increaseBalance(id, amount);
    }






}
