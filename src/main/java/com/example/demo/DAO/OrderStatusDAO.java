package com.example.demo.DAO;

import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.models.User;

import java.util.List;

public interface OrderStatusDAO extends CommonDAO<OrderStatus, Long> {
    OrderStatus findByStatus(String status);

}