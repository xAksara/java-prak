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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user));
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
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("status"), status));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Order> findByPaymentMethod(PaymentMethod paymentMethod) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
