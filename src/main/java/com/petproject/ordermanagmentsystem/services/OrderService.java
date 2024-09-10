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
import com.petproject.ordermanagmentsystem.utils.OrderStatus;
import com.petproject.ordermanagmentsystem.utils.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final OrderProductRepository orderProductRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Transactional
    public void createNewOrder(int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId + " does not exist"));
        Order order = newOrder(customer);
        orderRepository.save(order);
    }


    public List<Order> getAllOrders(int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id"+customerId+ "does not exist"));

        return orderRepository.findByOwner(customer).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+customerId+ " has no orders"));
    }

    public Order getOrderById(int id, int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+customerId+"  does not exist"));
        return orderRepository.findByOwnerAndId(customer, customerId).orElse(null);
    }

    @Transactional
    public Order updateOrderById(int id, Order order) {
        if(orderRepository.existsById(id)) {
            order.setId(id);
            return orderRepository.save(order);
        }
        else throw new ResourceNotFoundException("Order with id "+ id + " does not exist!");
    }

    @Transactional
    public void deleteOrderById(int id, int customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId+ " does not exist!"));
        Order order = orderRepository.findByOwnerAndId(customer, id).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ id + " does not exist"));

        orderRepository.delete(order);

    }

    @Transactional
    public void addProductToOrder(int productId,  int customerId){

        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id"+customerId + "does not exist"));

        Order order;
        if(orderRepository.findByStatusAndOwner(OrderStatus.CART, customer).isEmpty()){
            order = newOrder(customer);
            orderRepository.save(order);
        }
        else{
            order = orderRepository.findByStatusAndOwner(OrderStatus.CART, customer).orElseThrow(()->
                    new EntityNotFoundException("Order with status 'CART' does not exist"));

        }

        Product product = productRepository.findById(productId).orElseThrow(()->
                new EntityNotFoundException("Product with id "+ productId + " does not exist"));


        if (!order.getProducts().contains(product) && product.getQuantity()>0){
            order.getProducts().add(product);
            product.setQuantity(product.getQuantity()-1);
            OrderProduct orderProduct = orderProductRepository.findById(new OrderProductId(order.getId(), productId)).
                    orElseThrow(()->new EntityNotFoundException("Order product with id ("+ order.getId()+","+productId+") not found"));
            orderProduct.setQuantity(1);
            orderProductRepository.save(orderProduct);
            orderRepository.save(order);
        }

    }

    @Transactional
    public void deleteProductFromOrder(int productId, int orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new EntityNotFoundException("This order does not exist"));
        if(order.getStatus()==OrderStatus.CART){

            OrderProduct orderProduct = orderProductRepository.findById(new OrderProductId(orderId, productId)).
                orElseThrow(()->new EntityNotFoundException("Order product with id ("+ orderId+","+productId+") not found"));
        orderProductRepository.delete(orderProduct);

        }
    }

    @Transactional
    public void changeStatus(int orderId, int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId+ " does not exist"));

        Order order = orderRepository.findByOwnerAndId(customer, orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ orderId + " does not exist"));

        switch (order.getStatus()){
            case CART -> {
                order.setStatus(OrderStatus.PLACE);

                List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);

                for (OrderProduct orderProduct : orderProducts) {
                    Product product = orderProduct.getProduct();
                    int orderedQuantity = orderProduct.getQuantity();
                    // Проверяем, что на складе достаточно товара для выполнения заказа
                    if (product.getQuantity() < orderedQuantity) {
                        throw new IllegalStateException("Not enough stock for product: " + product.getName());
                    }
                    // Уменьшаем количество товара на складе
                    product.setQuantity(product.getQuantity() - orderedQuantity);
                    productRepository.save(product);  // Сохраняем изменения в базе
                }
            }
            case PLACE -> order.setStatus(OrderStatus.ON_THE_WAY);
            case ON_THE_WAY -> order.setStatus(OrderStatus.DELIVERED);
            case DELIVERED -> {
                return;
            }
        }


        orderRepository.save(order);
    }

    public Optional<Order> getCartOrder(int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId+ " does not exist"));

        return orderRepository.findByStatusAndOwner(OrderStatus.CART, customer);

    }

    public Optional<List<Order>> getAllOrdersInProcess(int customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId+ " does not exist"));

        return orderRepository.findByStatusNotAndOwner(OrderStatus.CART, customer);

    }

    public static Order newOrder(Customer customer){
        Order order = new Order();
        order.setOrderDateTime(new Timestamp(System.currentTimeMillis()));
        order.setOwner(customer);
        order.setStatus(OrderStatus.CART);
        return order;
    }

    public List<Product> getProductsByOrderId(int customerId, int orderId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->
                new EntityNotFoundException("Customer with id "+ customerId + " does not exist"));

        Order order = orderRepository.findByOwnerAndId(customer, orderId).orElseThrow(()->
                new EntityNotFoundException("Order with id "+ orderId + " does not exist"));

        return order.getProducts();

    }

}
