package com.example.demo.DAO.Impl;

import com.example.demo.DAO.UserDAO;
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
public class UserDAOImpl extends CommonDAOImpl<User, Long> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("login"), login));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }

    @Override
    public boolean existsByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(criteriaBuilder.count(root)).where(criteriaBuilder.equal(root.get("login"), login));
            return session.createQuery(criteriaQuery).getSingleResult() > 0;
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("lastName"), lastName));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("firstName"), firstName));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<User> findBySurname(String surname) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("surname"), surname));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    @Override
    public List<User> findByPhone(String phone) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("phone"), phone));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    @Override
    public List<User> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("email"), email));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
    @Override
    public List<User> findByAddress(String address) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("address"), address));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<User> findAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.isTrue(root.get("isAdmin")));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
