package com.example.demo.DAO.Impl;

import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.models.BlenderAttributes;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional
public class CategoryDAOImpl extends CommonDAOImpl<Category, Long> implements CategoryDAO {

    public CategoryDAOImpl() {
        super(Category.class);
    }

    @Override
    public Long getIdByName(String category) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.select(root.get("id")).where(criteriaBuilder.equal(root.get("category"), category));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }
}
