package com.example.shoppingapi.dao;

import com.example.shoppingapi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailrepository extends JpaRepository<OrderDetail, Long> {
}
