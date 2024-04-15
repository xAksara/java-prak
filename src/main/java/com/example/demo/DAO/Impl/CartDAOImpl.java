package com.example.demo.DAO.Impl;

import com.example.demo.DAO.CartDAO;
import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;
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
public class CartDAOImpl extends CommonDAOImpl<Cart, Cart.CartPK> implements CartDAO {

    public CartDAOImpl() {
        super(Cart.class);
    }

    @Override
    public List<Cart> findByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
            Root<Cart> root = criteriaQuery.from(Cart.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Cart> findByProduct(Product product) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
            Root<Cart> root = criteriaQuery.from(Cart.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("product"), product));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
