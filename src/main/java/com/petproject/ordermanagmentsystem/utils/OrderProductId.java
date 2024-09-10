package com.petproject.ordermanagmentsystem.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class OrderProductId implements Serializable {
    @Column(name="order_id")
    private int orderId;

    @Column(name="product_id")
    private int productId;

}
