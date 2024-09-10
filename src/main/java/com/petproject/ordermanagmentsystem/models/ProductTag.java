package com.petproject.ordermanagmentsystem.models;

import com.petproject.ordermanagmentsystem.utils.ProductTagId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product_tag")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductTag {
    @EmbeddedId
    private ProductTagId productTagId;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
