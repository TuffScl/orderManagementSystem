package com.petproject.ordermanagmentsystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Product> products;
}
