package com.example.shoppingapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, insertable = false)
    Date createAt;

    @CreationTimestamp
    @Column(nullable = false)
    Date updateAt;

    @Column(columnDefinition = "boolean default false")
    Boolean isCanceled;

    @Column(columnDefinition = "boolean default false")
    Boolean isPaid;

    @ManyToOne
    User user;

    @ManyToMany
    @JoinTable(
            name = "order_detail_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<Product> products;

    String message;

    String paymentMethod;
}
