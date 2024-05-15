package com.example.demo.DAO.Impl;

import com.example.demo.DAO.Impl.CommonDAOImpl;
import com.example.demo.DAO.PhoneAttributesDAO;
import com.example.demo.models.BlenderAttributes;
import com.example.demo.models.PhoneAttributes;
import com.example.demo.models.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.JoinType;
import java.util.List;

@Repository
@Transactional
public class PhoneAttributesDAOImpl extends CommonDAOImpl<PhoneAttributes, Long> implements PhoneAttributesDAO {

    public PhoneAttributesDAOImpl() {
        super(PhoneAttributes.class);
    }

    @Override
    public List<PhoneAttributes> findByScreenDiagonalGreaterThanEqual(Double screenDiagonal) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("screenDiagonal"), screenDiagonal));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByScreenDiagonalLessThanEqual(Double screenDiagonal) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("screenDiagonal"), screenDiagonal));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByProcessor(String processor) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("processor"), processor));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByMemorySizeGreaterThanEqual(Integer memorySize) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("memorySize"), memorySize));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByMemorySizeLessThanEqual(Integer memorySize) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("memorySize"), memorySize));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByRamSizeLessThanEqual(Integer ramSize) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.lessThanOrEqualTo(root.get("ramSize"), ramSize));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    @Override
    public List<PhoneAttributes> findByRamSizeGreaterThanEqual(Integer ramSize) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.greaterThanOrEqualTo(root.get("ramSize"), ramSize));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<PhoneAttributes> findByScreenType(String screenType) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("screenType"), screenType));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public List<PhoneAttributes> findByProductBrand(String brand) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<PhoneAttributes> criteriaQuery = criteriaBuilder.createQuery(PhoneAttributes.class);
            Root<PhoneAttributes> root = criteriaQuery.from(PhoneAttributes.class);

            Join<PhoneAttributes, Product> productJoin = root.join("product", JoinType.INNER);

            criteriaQuery.where(criteriaBuilder.equal(productJoin.get("brand"), brand));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
