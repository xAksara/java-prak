package com.example.demo.DAO.Impl;

import com.example.demo.DAO.OrderDAO;
import com.example.demo.DAO.OrderStatusDAO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
    public OrderStatus findByStatus(String status) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderStatus> criteriaQuery = criteriaBuilder.createQuery(OrderStatus.class);
            Root<OrderStatus> root = criteriaQuery.from(OrderStatus.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("status"), status));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }
}