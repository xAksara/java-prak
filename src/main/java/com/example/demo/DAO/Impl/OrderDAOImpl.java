package com.example.demo.DAO.Impl;

import com.example.demo.DAO.Impl.CommonDAOImpl;
import com.example.demo.DAO.OrderDAO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.models.PaymentMethod;
import com.example.demo.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class OrderDAOImpl extends CommonDAOImpl<Order, Long> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user").get("id"), user.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Order> findByOrderDate(Date orderDate) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("orderDate"), orderDate));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Order> findByDeliveryDate(Date deliveryDate) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("deliveryDate"), deliveryDate));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("status").get("id"), status.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Order> findByPaymentMethod(PaymentMethod paymentMethod) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("paymentMethod").get("id"), paymentMethod.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
