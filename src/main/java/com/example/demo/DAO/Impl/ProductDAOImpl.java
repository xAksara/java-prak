package com.example.demo.DAO.Impl;

import com.example.demo.DAO.ProductDAO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
@Repository
@Transactional
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }

    @Override
    public Product findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }

    @Override
    public List<Product> findByBrand(String brand) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("brand"), brand));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("category"), category));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findByPriceGreaterThanEqual(Long price) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findByPriceLessThanEqual(Long price) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("price"), price));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findByQuantityGreaterThanEqual(Integer quantity) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), quantity));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findByQuantityLessThanEqual(Integer quantity) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), quantity));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Product> findAllByOrderByBoughtDesc() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bought")));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

}
