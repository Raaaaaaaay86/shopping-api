package com.example.shoppingapi.service;

import com.example.shoppingapi.dao.OrderDetailrepository;
import com.example.shoppingapi.dao.UserRepository;
import com.example.shoppingapi.dto.DefaultRequest;
import com.example.shoppingapi.dto.DefaultResponse;
import com.example.shoppingapi.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailrepository orderDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public OrderDetail createOrder(OrderDetail newOrder) {
        return orderDetailRepository.save(newOrder);
    }

    @Override
    public Boolean cancelOrder(Long orderId) {
        var targetOrderOptional = orderDetailRepository.findById(orderId);

        if (targetOrderOptional.isEmpty()) {
            return false;
        }
        var targetOrder = targetOrderOptional.get();
        targetOrder.setIsCanceled(true);

        return true;
    }

    @Override
    public List<OrderDetail> getAllOrderByUserId(Long userId) {
        var targetUserOptional = userRepository.findById(userId);

        if (targetUserOptional.isEmpty()) {
            return null;
        }
        var targetUser = targetUserOptional.get();

        return targetUser.getOrders();
    }
}
