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
public class ToasterAttributesDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private ToasterAttributesDAO toasterAttributesDAO;

    @Autowired
    private SessionFactory sessionFactory;
    @Test
    void testFindBySlotsGreaterThanEqual() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByNumberOfSlotsGreaterThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getNumberOfSlots() >= 112));
    }
    @Test
    void testFindBySlotsLessThanEqual() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByNumberOfSlotsLessThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getNumberOfSlots() <= 112));
    }
    @Test
    void testFindByHasTimer() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByHasTimer(false);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().noneMatch(ToasterAttributes::getHasTimer));
    }
    @Test
    void testFindByPowerOutputGreaterThanEqual()
    {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByPowerOutputGreaterThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getPowerOutput() >= 112));

    }
    @Test
    void testFindByPowerOutputLessThanEqual() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByPowerOutputLessThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getPowerOutput() <= 112));

    }
    @Test
    void testFindByHasBreadSensor() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByHasBreadSensor(true);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(ToasterAttributes::getHasBreadSensor));

    }

    @Test
    void testFindByHasDisplay() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByHasDisplay(false);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().noneMatch(ToasterAttributes::getHasDisplay));
    }
    @Test
    void testFindByProductBrand() {
        List<ToasterAttributes> attributesList = toasterAttributesDAO.findByProductBrand("Brand2");
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> productDAO.getById(o.getId()).getBrand().equals("Brand2")));
    }

    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE prak.public.attributes_toaster RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }

        List<Product> productList = new ArrayList<>();
        List<ToasterAttributes> toasterList = new ArrayList<>();

        Category toaster = categoryDAO.getById(categoryDAO.getIdByName("toaster"));
        Category phone = categoryDAO.getById(categoryDAO.getIdByName("phone"));
        Product product1 = new Product();
        product1.setName("Product1");
        product1.setPrice(10L);
        product1.setBrand("Brand1");
        product1.setCategory(toaster);
        product1.setDescription("Description1");
        product1.setQuantity(100);
        product1.setBought(50);
        product1.setImgPath("path/to/image1");
        productList.add(product1);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(20L);
        product2.setBrand("Brand2");
        product2.setCategory(toaster);
        product2.setDescription("Description2");
        product2.setQuantity(200);
        product2.setBought(100);
        product2.setImgPath("path/to/image2");
        productList.add(product2);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setPrice(30L);
        product3.setBrand("Brand2");
        product3.setCategory(toaster);
        product3.setDescription("Description3");
        product3.setQuantity(300);
        product3.setBought(150);
        product3.setImgPath("path/to/image3");
        productList.add(product3);

        productDAO.saveCollection(productList);

        ToasterAttributes ba1 = new ToasterAttributes();
        ba1.setHasBreadSensor(true);
        ba1.setProduct(product1);
        ba1.setHasDisplay(true);
        ba1.setHasTimer(true);
        ba1.setPowerOutput(111);
        ba1.setNumberOfSlots(111);
        toasterList.add(ba1);

        ToasterAttributes ba2 = new ToasterAttributes();
        ba2.setHasBreadSensor(true);
        ba2.setProduct(product2);
        ba2.setHasDisplay(false);
        ba2.setHasTimer(false);
        ba2.setPowerOutput(222);
        ba2.setNumberOfSlots(222);
        toasterList.add(ba2);

        ToasterAttributes ba3 = new ToasterAttributes();
        ba3.setHasBreadSensor(false);
        ba3.setProduct(product3);
        ba3.setHasDisplay(false);
        ba3.setHasTimer(false);
        ba3.setPowerOutput(222);
        ba3.setNumberOfSlots(222);
        toasterList.add(ba3);

        toasterAttributesDAO.saveCollection(toasterList);
    }
}
