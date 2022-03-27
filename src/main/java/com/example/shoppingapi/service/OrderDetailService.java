package com.example.shoppingapi.service;

import com.example.shoppingapi.entity.OrderDetail;

import javax.persistence.criteria.Order;
import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrder(OrderDetail newOrder);
    Boolean cancelOrder(Long orderId);
    List<OrderDetail> getAllOrderByUserId(Long userId);
}
