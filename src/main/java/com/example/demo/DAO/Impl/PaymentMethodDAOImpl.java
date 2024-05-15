package com.example.demo.DAO.Impl;

import com.example.demo.DAO.PaymentMethodDAO;
import com.example.demo.models.PaymentMethod;
import com.example.demo.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PaymentMethodDAOImpl extends CommonDAOImpl<PaymentMethod, Long> implements PaymentMethodDAO {
    public PaymentMethodDAOImpl() {
        super(PaymentMethod.class);
    }
    public PaymentMethod findByMethod(String method) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentMethod> criteriaQuery = criteriaBuilder.createQuery(PaymentMethod.class);
            Root<PaymentMethod> root = criteriaQuery.from(PaymentMethod.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("method"), method));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }
}
