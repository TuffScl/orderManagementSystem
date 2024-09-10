package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
