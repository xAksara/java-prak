package com.example.demo.DAO.Impl;

import com.example.demo.DAO.Impl.CommonDAOImpl;
import com.example.demo.DAO.ToasterAttributesDAO;
import com.example.demo.models.BlenderAttributes;
import com.example.demo.models.Product;
import com.example.demo.models.ToasterAttributes;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class ToasterAttributesDAOImpl extends CommonDAOImpl<ToasterAttributes, Long> implements ToasterAttributesDAO {

    public ToasterAttributesDAOImpl() {
        super(ToasterAttributes.class);
    }

    @Override
    public List<ToasterAttributes> findByPowerOutputGreaterThanEqual(Integer powerOutput) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("powerOutput"), powerOutput));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByPowerOutputLessThanEqual(Integer powerOutput) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("powerOutput"), powerOutput));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByNumberOfSlotsGreaterThanEqual(Integer numberOfSlots) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("numberOfSlots"), numberOfSlots));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByNumberOfSlotsLessThanEqual(Integer numberOfSlots) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("numberOfSlots"), numberOfSlots));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByHasDisplay(Boolean hasDisplay) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hasDisplay"), hasDisplay));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByHasTimer(Boolean hasTimer) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hasTimer"), hasTimer));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByHasBreadSensor(Boolean hasBreadSensor) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("hasBreadSensor"), hasBreadSensor));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ToasterAttributes> findByProductBrand(String brand) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<ToasterAttributes> criteriaQuery = criteriaBuilder.createQuery(ToasterAttributes.class);
            Root<ToasterAttributes> root = criteriaQuery.from(ToasterAttributes.class);

            Join<BlenderAttributes, Product> productJoin = root.join("product", JoinType.INNER);

            criteriaQuery.where(criteriaBuilder.equal(productJoin.get("brand"), brand));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
