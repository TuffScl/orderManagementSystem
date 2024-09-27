package com.petproject.ordermanagmentsystem.services;

import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.models.OrderProduct;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.repositories.CustomerRepository;
import com.petproject.ordermanagmentsystem.repositories.OrderProductRepository;
import com.petproject.ordermanagmentsystem.repositories.OrderRepository;
import com.petproject.ordermanagmentsystem.repositories.ProductRepository;
import com.petproject.ordermanagmentsystem.utils.OrderProductId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
@Service
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public List<OrderProduct> getOrderProducts(int orderId, int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId + " does not exist"));
        Order order = orderRepository.findByOwnerAndId(customer, orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id " + orderId + " does not exist"));

        return orderProductRepository.findByOrder(order);
    }



    @Transactional
    public void changeQuantityProductFromOrder(int orderId, int productId, boolean isIncrease) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new EntityNotFoundException("Order not found"));
        Product product = productRepository.findById(productId).orElseThrow(()->
                new EntityNotFoundException("Product not found"));

        OrderProductId orderProductId = new OrderProductId(orderId, productId);
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElseThrow(()->
                new EntityNotFoundException("Some troubles"));

        if(isIncrease && product.getQuantity()>0){
            orderProduct.setQuantity(orderProduct.getQuantity()+1);
            //product.setQuantity(product.getQuantity()-1);
        }
        else if (orderProduct.getQuantity()>1){
            orderProduct.setQuantity(orderProduct.getQuantity()-1);
            //product.setQuantity(product.getQuantity()+1);
        }
        orderProductRepository.save(orderProduct);
    }
}

