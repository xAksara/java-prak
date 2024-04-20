package com.example.demo.DAO;

import com.example.demo.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PhoneAttributesDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private PhoneAttributesDAO phoneAttributesDAO;

    @Autowired
    private SessionFactory sessionFactory;
    @Test
    void testFindByScreenDiagonalGreaterThanEqual() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByScreenDiagonalGreaterThanEqual(6.2);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getScreenDiagonal() >= 6.2));
    }

    @Test
    void testFindByScreenDiagonalLessThanEqual() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByScreenDiagonalLessThanEqual(7.);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getScreenDiagonal() <= 7));
    }
    @Test
    void testFindByProcessor() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByProcessor("processor1");
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getProcessor().equals("processor1")));
    }
    @Test
    void findByScreenType() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByScreenType("type1");
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getScreenType().equals("type1")));
    }
    @Test
    void testFindByMemorySizeGreaterThanEqual()
    {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByMemorySizeGreaterThanEqual(65);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getMemorySize() >= 65));

    }
    @Test
    void testFindByMemorySizeLessThanEqual() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByMemorySizeLessThanEqual(65);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getMemorySize() <= 65));

    }
    @Test
    void testFindByRamSizeGreaterThanEqual()
    {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByRamSizeGreaterThanEqual(8);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getRamSize() >= 8));

    }
    @Test
    void testFindByRamSizeLessThanEqual() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByRamSizeLessThanEqual(8);
        assertNotNull(attributesList);
        assertEquals(3, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getRamSize() <= 8));

    }
    @Test
    void testFindByProductBrand() {
        List<PhoneAttributes> attributesList = phoneAttributesDAO.findByProductBrand("Brand2");
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> productDAO.getById(o.getId()).getBrand().equals("Brand2")));
    }

    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE prak.public.attributes_phone RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }

        List<Product> productList = new ArrayList<>();
        List<PhoneAttributes> phoneList = new ArrayList<>();

        Category phone = categoryDAO.getById(categoryDAO.getIdByName("phone"));
        Product product1 = new Product();
        product1.setName("Product1");
        product1.setPrice(10L);
        product1.setBrand("Brand1");
        product1.setCategory(phone);
        product1.setDescription("Description1");
        product1.setQuantity(100);
        product1.setBought(50);
        product1.setImgPath("path/to/image1");
        productList.add(product1);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(20L);
        product2.setBrand("Brand2");
        product2.setCategory(phone);
        product2.setDescription("Description2");
        product2.setQuantity(200);
        product2.setBought(100);
        product2.setImgPath("path/to/image2");
        productList.add(product2);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setPrice(30L);
        product3.setBrand("Brand2");
        product3.setCategory(phone);
        product3.setDescription("Description3");
        product3.setQuantity(300);
        product3.setBought(150);
        product3.setImgPath("path/to/image3");
        productList.add(product3);

        productDAO.saveCollection(productList);

        PhoneAttributes ba1 = new PhoneAttributes();
        ba1.setProcessor("processor1");
        ba1.setProduct(product1);
        ba1.setMemorySize(64);
        ba1.setRamSize(4);
        ba1.setScreenDiagonal(6.1);
        ba1.setScreenType("type1");
        phoneList.add(ba1);

        PhoneAttributes ba2 = new PhoneAttributes();
        ba2.setProcessor("processor2");
        ba2.setProduct(product2);
        ba2.setMemorySize(64);
        ba2.setRamSize(8);
        ba2.setScreenDiagonal(6.2);
        ba2.setScreenType("type2");
        phoneList.add(ba2);

        PhoneAttributes ba3 = new PhoneAttributes();
        ba3.setProcessor("processor3");
        ba3.setProduct(product3);
        ba3.setMemorySize(128);
        ba3.setRamSize(8);
        ba3.setScreenDiagonal(7.1);
        ba3.setScreenType("type3");
        phoneList.add(ba3);

        phoneAttributesDAO.saveCollection(phoneList);
    }
}
