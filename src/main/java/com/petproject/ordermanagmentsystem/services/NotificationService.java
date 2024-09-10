package com.petproject.ordermanagmentsystem.services;


import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.models.Notification;
import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.repositories.CustomerRepository;
import com.petproject.ordermanagmentsystem.repositories.NotificationRepository;
import com.petproject.ordermanagmentsystem.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.notificationRepository = notificationRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Notification getNotificationByCustomerAndOrderAndId(int notificationId, int customerId, int orderId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
            new EntityNotFoundException("Customer with id "+customerId +" does not exist"));
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ orderId+" does not exist" ));
       return notificationRepository.findByIdAndCustomerAndOrder(notificationId,customer,order).orElseThrow(()->
                new EntityNotFoundException("Notification with id "+ notificationId+" does not exist"));
    }

    public List<Notification> getAllNotificationsByCustomerAndOrder(int customerId, int orderId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+customerId +" does not exist"));
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ orderId+" does not exist" ));
        return notificationRepository.findAllByCustomerAndOrder(customer,order).orElseThrow(()->
                new EntityNotFoundException("There are no notifications"));
    }

    public List<Notification> getAllNotificationsByCustomer(int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+customerId +" does not exist"));
        return notificationRepository.findAllByCustomer(customer).orElseThrow(()->
                new EntityNotFoundException("There are no notifications"));
    }

    @Transactional
    public void createNewNotification(int orderId, int customerId ){
        Notification notification = new Notification();
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ orderId + " does not exist"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId + " does not exist"));
        notification.setCustomer(customer);
        notification.setOrder(order);
        setMessageForNotification(notification);
        notification.setNotificationDate(new Timestamp(System.currentTimeMillis()));

        notificationRepository.save(notification);
    }

    private static void setMessageForNotification(Notification notification){
        switch (notification.getOrder().getStatus()){
            case PLACE -> notification.setMessage("Your order №"+notification.getOrder().getId()+ " has been successfully placed!");
            case ON_THE_WAY -> notification.setMessage("Your order №"+notification.getOrder().getId()+ " is on the way!");
            case DELIVERED -> notification.setMessage("Your order №"+notification.getOrder().getId()+ " has been successfully delivered!");
            default -> {
                return;
            }
        }
    }
}
