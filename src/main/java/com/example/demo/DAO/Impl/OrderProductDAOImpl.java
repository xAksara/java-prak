package com.example.demo.DAO.Impl;

import com.example.demo.DAO.OrderProductDAO;
import com.example.demo.models.OrderProduct;
import com.example.demo.models.Product;
import com.example.demo.models.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class OrderProductDAOImpl extends CommonDAOImpl<OrderProduct, OrderProduct.OrderProductPK> implements OrderProductDAO {

    public OrderProductDAOImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<OrderProduct> findByOrder(Order order) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);
            Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("order").get("id"), order.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<OrderProduct> findByProduct(Product product) {
        if (product == null) {
            return Collections.emptyList();
        }
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);
            Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("product").get("id"), product.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
