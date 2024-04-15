package com.example.demo.DAO;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    public void testFindByName() {
        Product foundProduct = productDAO.findByName("Product1");
        assertNotNull(foundProduct);
        assertEquals("Product1", foundProduct.getName());

        foundProduct = productDAO.findByName("Product1921480");
        assertNull(foundProduct);
    }

    @Test
    public void testFindByBrand() {
        List<Product> products = productDAO.findByBrand("Brand1");
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getBrand().equals("Brand1")));

        products = productDAO.findByBrand("Brand19240124");
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindByCategory() {
        Category category = categoryDAO.getById(1L);
        List<Product> products = productDAO.findByCategory(category);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getCategory().getId().equals(category.getId())));

        products = productDAO.findByCategory(null);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindByPriceGreaterThanEqual() {
        List<Product> products = productDAO.findByPriceGreaterThanEqual(20L);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getPrice() >= 20L));

        products = productDAO.findByPriceGreaterThanEqual(200000L);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindByPriceLessThanEqual() {
        List<Product> products = productDAO.findByPriceLessThanEqual(20L);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getPrice() <= 20L));

        products = productDAO.findByPriceLessThanEqual(0L);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindByQuantityGreaterThanEqual() {
        List<Product> products = productDAO.findByQuantityGreaterThanEqual(200);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getQuantity() >= 200));

        products = productDAO.findByPriceGreaterThanEqual(200000L);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindByQuantityLessThanEqual() {
        List<Product> products = productDAO.findByQuantityLessThanEqual(200);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(p -> p.getQuantity() <= 200));

        products = productDAO.findByPriceLessThanEqual(0L);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindAllByOrderByBoughtDesc() {
        List<Product> products = productDAO.findAllByOrderByBoughtDesc();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(isSortedDescendingByBought(products));
    }

    private boolean isSortedDescendingByBought(List<Product> products) {
        for (int i = 1; i < products.size(); i++) {
            if (products.get(i - 1).getBought() < products.get(i).getBought()) {
                return false;
            }
        }
        return true;
    }
    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();

        }
        List<Product> productList = new ArrayList<>();

        Category category = categoryDAO.getById(1L);
        Product product1 = new Product();
        product1.setName("Product1");
        product1.setPrice(10L);
        product1.setBrand("Brand1");
        product1.setCategory(category);
        product1.setDescription("Description1");
        product1.setQuantity(100);
        product1.setBought(50);
        product1.setImgPath("path/to/image1");
        productList.add(product1);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(20L);
        product2.setBrand("Brand2");
        product2.setCategory(category);
        product2.setDescription("Description2");
        product2.setQuantity(200);
        product2.setBought(100);
        product2.setImgPath("path/to/image2");
        productList.add(product2);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setPrice(30L);
        product3.setBrand("Brand3");
        product3.setCategory(category);
        product3.setDescription("Description3");
        product3.setQuantity(300);
        product3.setBought(150);
        product3.setImgPath("path/to/image3");
        productList.add(product3);

        productDAO.saveCollection(productList);
    }
}
