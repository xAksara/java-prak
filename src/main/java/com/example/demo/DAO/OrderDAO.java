package com.example.demo.DAO;

import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.models.PaymentMethod;
import com.example.demo.models.User;

import java.util.Date;
import java.util.List;

    public interface OrderDAO extends CommonDAO<Order, Long> {
        List<Order> findByUser(User user);
        List<Order> findByOrderDate(Date orderDate);
        List<Order> findByDeliveryDate(Date deliveryDate);
        List<Order> findByStatus(OrderStatus status);
        List<Order> findByPaymentMethod(PaymentMethod paymentMethod);
    }