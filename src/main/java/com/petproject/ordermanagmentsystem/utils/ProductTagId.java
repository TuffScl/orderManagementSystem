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

public class ProductTagId implements Serializable {

    @Column(name="product_id")
    private int productId;

    @Column(name="tag_id")
    private int tagId;

}
