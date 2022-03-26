package com.example.shoppingapi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "boolean default false")
    Boolean isEnabled;

    @Column(nullable = false)
    Integer num;

    @Column(nullable = false)
    Integer originPrice;

    @Column(nullable = false)
    Integer price = originPrice;

    @Column(nullable = false)
    String category;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String unit;

    @ManyToMany(mappedBy = "products")
    Set<Cart> carts;

    @ManyToMany(mappedBy = "products")
    Set<OrderDetail> order;

    String content;

    String description;

    String imageUrl;
}
