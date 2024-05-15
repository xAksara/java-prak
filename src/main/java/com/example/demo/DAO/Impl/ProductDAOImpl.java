package com.example.demo.DAO.Impl;

import com.example.demo.DAO.ProductDAO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import org.hibernate.Session;
@Repository
@Transactional
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }

    public Collection<Product> findByFilters(Map<String, String> filters) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            List<Predicate> predicates = new ArrayList<>();
            Join<?, ?> join = null;

            String category = filters.get("category");

            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("category"), category));

                join = switch (category) {
                    case "phone" -> root.join("phoneAttributes", JoinType.LEFT);
                    case "blender" -> root.join("blenderAttributes", JoinType.LEFT);
                    case "toaster" -> root.join("toasterAttributes", JoinType.LEFT);
                    default -> join;
                };
            }

            Join<?, ?> finalJoin = join;
            filters.forEach((key, value) -> {
                if (!key.equals("category") && !value.isEmpty()) {
                    switch (key) {
                        case "brand":
                            predicates.add(criteriaBuilder.equal(root.get("brand"), value));
                            break;
                        case "minPrice":
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.parseInt(value)));
                            break;
                        case "maxPrice":
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Integer.parseInt(value)));
                            break;
                        default:
                            if (finalJoin != null) {
                                switch (category) {
                                    case "phone":
                                        handlePhoneAttributes(key, value, finalJoin, predicates, criteriaBuilder);
                                        break;
                                    case "blender":
                                        handleBlenderAttributes(key, value, finalJoin, predicates, criteriaBuilder);
                                        break;
                                    case "toaster":
                                        handleToasterAttributes(key, value, finalJoin, predicates, criteriaBuilder);
                                        break;
                                }
                            }
                            break;
                    }
                }
            });

            String sortParam = filters.get("sort");
            if (sortParam != null && !sortParam.isEmpty()) {
                switch (sortParam) {
                    case "price_asc":
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
                        break;
                    case "price_desc":
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
                        break;
                    case "sales_desc":
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bought")));
                        break;
                }
            }

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    private void handlePhoneAttributes(String key, String value, Join<?, ?> join, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "screenDiagonal":
            case "processor":
            case "memorySize":
            case "ramSize":
            case "screenType":
                predicates.add(criteriaBuilder.equal(join.get(key), value));
                break;
        }
    }

    private void handleBlenderAttributes(String key, String value, Join<?, ?> join, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        switch (key) {
            case "capacity":
            case "powerOutput":
            case "numberOfSpeeds":
                predicates.add(criteriaBuilder.equal(join.get(key), value));
                break;
            case "hasTimer":
            case "hasDisplay":
                predicates.add(criteriaBuilder.equal(join.get(key), Boolean.parseBoolean(value)));
                break;
        }
    }

    private void handleToasterAttributes(String key, String value, Join<?, ?> join, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        System.out.print(key + "  " + value + "\n\n\n");
        switch (key) {
            case "powerOutput":
            case "numberOfSlots":
                predicates.add(criteriaBuilder.equal(join.get(key), value));
                break;
            case "hasDisplay":
            case "hasTimer":
            case "hasBreadSensor":
                predicates.add(criteriaBuilder.equal(join.get(key), Boolean.parseBoolean(value)));
                break;
        }
    }


    //    @Override
//    public Collection<Product> findByFilters(Map<String, String> filters) {
//        try (Session session = sessionFactory.openSession()) {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
//            Root<Product> root = criteriaQuery.from(Product.class);
//
//            // Создаем список предикатов для применения фильтров
//            List<Predicate> predicates = new ArrayList<>();
//
//            // Применяем фильтры
//            filters.forEach((key, value) -> {
//                switch (key) {
//                    case "brand":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("brand"), value));
//                        }
//                        break;
//                    case "category":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("category").get("category"), value));
//                        }
//                        break;
//                    case "minPrice":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Integer.parseInt(value)));
//                        }
//                        break;
//                    case "maxPrice":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Integer.parseInt(value)));
//                        }
//                        break;
//                    //PHONE
//                    case "screenDiagonal":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("screenDiagonal"), value));
//                        }
//                        break;
//                    case "processor":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("processor"), value));
//                        }
//                        break;
//                    case "memorySize":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("memorySize"), value));
//                        }
//                        break;
//                    case "ramSize":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("ramSize"), value));
//                        }
//                        break;
//                    case "screenType":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("screenType"), value));
//                        }
//                        break;
//                    // BLENDER
//                    case "capacity":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("capacity"), value));
//                        }
//                        break;
//                    case "hasTimer":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("hasTimer"), value));
//                        }
//                        break;
//                    case "powerOutput":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("powerOutput"), value));
//                        }
//                        break;
//                    case "numberOfSpeeds":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("numberOfSpeeds"), value));
//                        }
//                        break;
//                    case "hasDisplay":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("hasDisplay"), value));
//                        }
//                        break;
//                    //TOASTER
//                    case "numberOfSlots":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("numberOfSlots"), value));
//                        }
//                        break;
//                    case "hasBreadSensor":
//                        if (!value.isEmpty()) {
//                            predicates.add(criteriaBuilder.equal(root.get("hasBreadSensor"), value));
//                        }
//                        break;
//                }
//            });
//
//            // Применяем сортировку
//            String sortParam = filters.get("sort");
//            if (sortParam != null && !sortParam.isEmpty()) {
//                switch (sortParam) {
//                    case "price_asc":
//                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
//                        break;
//                    case "price_desc":
//                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
//                        break;
//                    case "sales_desc":
//                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("bought")));
//                        break;
//                }
//            }
//
//            // Применяем все предикаты
//            criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
//
//            return session.createQuery(criteriaQuery).getResultList();
//        }
//    }
    @Override
    public Product findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
            return session.createQuery(criteriaQuery).uniqueResult();
        }
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
        if (category == null) {
            return Collections.emptyList();
        }
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
