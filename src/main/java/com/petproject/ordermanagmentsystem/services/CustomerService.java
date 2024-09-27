package com.petproject.ordermanagmentsystem.services;

import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.repositories.CustomerRepository;
import com.petproject.ordermanagmentsystem.utils.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void createNewCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public Customer getCustomerById(int id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public Customer updateCustomerById(int id, Customer customer) {
        if(customerRepository.existsById(id)){
            customer.setId(id);
            return customerRepository.save(customer);
        }
        else throw new ResourceNotFoundException("Customer with id "+ id + " does not exist!");
    }

    @Transactional
    public void deleteCustomerById(int id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
        }
        else throw new ResourceNotFoundException("Customer with id "+ id + " does not exist!");
    }

    @Transactional
    public void increaseBalance(int id, int amount){
        Customer customer = customerRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ id + " does not exist!"));
        customer.setBalance(customer.getBalance()+amount);
        customerRepository.save(customer);
    }

    @Transactional
    public void decreaseBalance(int id, int amount){
        Customer customer = customerRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ id + " does not exist!"));
        customer.setBalance(customer.getBalance()-amount);
        customerRepository.save(customer);
    }

}
