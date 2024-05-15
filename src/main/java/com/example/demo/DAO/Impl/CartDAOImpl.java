package com.example.demo.DAO.Impl;

import com.example.demo.DAO.CartDAO;
import com.example.demo.controllers.CartController;
import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional
public class CartDAOImpl extends CommonDAOImpl<Cart, Cart.CartPK> implements CartDAO {

    public CartDAOImpl() {
        super(Cart.class);
    }

    @Override
    public List<Cart> findByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
            Root<Cart> root = criteriaQuery.from(Cart.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user").get("id"), user.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Cart> findByProduct(Product product) {
        if (product == null) {
            return Collections.emptyList();
        }
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
            Root<Cart> root = criteriaQuery.from(Cart.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("product").get("id"), product.getId()));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
