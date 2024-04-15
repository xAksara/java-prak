package com.example.demo.DAO.Impl;

import com.example.demo.DAO.OrderProductDAO;
import com.example.demo.models.OrderProduct;
import com.example.demo.models.Product;
import com.example.demo.models.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.ArrayList;
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
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("order"), order));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<OrderProduct> findByProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);
            Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("product"), product));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
