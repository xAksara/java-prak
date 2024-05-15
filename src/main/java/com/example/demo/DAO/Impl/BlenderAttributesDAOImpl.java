package com.example.demo.DAO.Impl;

import com.example.demo.DAO.BlenderAttributesDAO;
import com.example.demo.models.BlenderAttributes;
import com.example.demo.models.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.*;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional

public class BlenderAttributesDAOImpl extends CommonDAOImpl<BlenderAttributes, Long>  implements BlenderAttributesDAO {

    public BlenderAttributesDAOImpl() {
        super(BlenderAttributes.class);
    }

    @Override
    public List<BlenderAttributes> findByCapacityGreaterThanEqual(Double capacity) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByCapacityLessThanEqual(Double capacity) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), capacity));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByHasTimer(Boolean hasTimer) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hasTimer"), hasTimer));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByPowerOutputGreaterThanEqual(Integer powerOutput) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("powerOutput"), powerOutput));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByNumberOfSpeedsGreaterThanEqual(Integer numberOfSpeeds) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfSpeeds"), numberOfSpeeds));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByHasDisplay(Boolean hasDisplay) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hasDisplay"), hasDisplay));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByPowerOutputLessThanEqual(Integer powerOutput) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("powerOutput"), powerOutput));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<BlenderAttributes> findByNumberOfSpeedsLessThanEqual(Integer numberOfSpeeds) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("numberOfSpeeds"), numberOfSpeeds));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    @Override
    public List<BlenderAttributes> findByProductBrand(String brand) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<BlenderAttributes> criteriaQuery = criteriaBuilder.createQuery(BlenderAttributes.class);
            Root<BlenderAttributes> root = criteriaQuery.from(BlenderAttributes.class);

            Join<BlenderAttributes, Product> productJoin = root.join("product", JoinType.INNER);

            criteriaQuery.where(criteriaBuilder.equal(productJoin.get("brand"), brand));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}