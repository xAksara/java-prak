package com.example.demo.DAO.Impl;

import com.example.demo.DAO.OrderStatusDAO;
import com.example.demo.models.OrderStatus;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional
public class OrderStatusDAOImpl extends CommonDAOImpl<OrderStatus, Long> implements OrderStatusDAO {
    public OrderStatusDAOImpl() {
        super(OrderStatus.class);
    }
}