package com.petproject.ordermanagmentsystem.models;

import com.petproject.ordermanagmentsystem.utils.OrderProductId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="order_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderProduct {
    @EmbeddedId
    private OrderProductId orderProductId;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;


    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="quantity")
    private int quantity;

}
