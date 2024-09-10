package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.models.OrderProduct;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.utils.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    List<OrderProduct> findByOrder(Order order);
    OrderProduct findByOrderAndProduct(Order order, Product product);
}
