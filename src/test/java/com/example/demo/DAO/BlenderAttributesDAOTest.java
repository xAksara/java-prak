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
public class BlenderAttributesDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private BlenderAttributesDAO blenderAttributesDAO;

    @Autowired
    private SessionFactory sessionFactory;
    @Test
    void testFindByCapacityGreaterThanEqual() {
            List<BlenderAttributes> attributesList = blenderAttributesDAO.findByCapacityGreaterThanEqual(112.);
            assertNotNull(attributesList);
            assertEquals(2, attributesList.size());
            assertTrue(attributesList.stream().allMatch(o -> o.getCapacity() >= 112));
        }
    @Test
    void testFindByCapacityLessThanEqual() {
            List<BlenderAttributes> attributesList = blenderAttributesDAO.findByCapacityLessThanEqual(112.);
            assertNotNull(attributesList);
            assertEquals(1, attributesList.size());
            assertTrue(attributesList.stream().allMatch(o -> o.getCapacity() <= 112));
        }
    @Test
    void testFindByHasTimer() {
            List<BlenderAttributes> attributesList = blenderAttributesDAO.findByHasTimer(false);
            assertNotNull(attributesList);
            assertEquals(2, attributesList.size());
            assertTrue(attributesList.stream().noneMatch(BlenderAttributes::getHasTimer));
        }
    @Test
    void testFindByPowerOutputGreaterThanEqual()
    {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByPowerOutputGreaterThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getPowerOutput() >= 112));

    }
    @Test
    void testFindByPowerOutputLessThanEqual() {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByPowerOutputLessThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getPowerOutput() <= 112));

    }
    @Test
    void testFindByNumberOfSpeedsGreaterThanEqual() {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByNumberOfSpeedsGreaterThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getNumberOfSpeeds() >= 112));

    }
    @Test
    void testFindByNumberOfSpeedsLessThanEqual() {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByNumberOfSpeedsLessThanEqual(112);
        assertNotNull(attributesList);
        assertEquals(1, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> o.getNumberOfSpeeds() <= 112));
    }
    @Test
    void testFindByHasDisplay() {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByHasDisplay(false);
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().noneMatch(BlenderAttributes::getHasDisplay));
    }
    @Test
    void testFindByProductBrand() {
        List<BlenderAttributes> attributesList = blenderAttributesDAO.findByProductBrand("Brand2");
        assertNotNull(attributesList);
        assertEquals(2, attributesList.size());
        assertTrue(attributesList.stream().allMatch(o -> productDAO.getById(o.getId()).getBrand().equals("Brand2")));
    }

    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE prak.public.attributes_blender RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();
        }

        List<Product> productList = new ArrayList<>();
        List<BlenderAttributes> blenderList = new ArrayList<>();

        Category blender = categoryDAO.getById(categoryDAO.getIdByName("blender"));
        Category phone = categoryDAO.getById(categoryDAO.getIdByName("phone"));
        Product product1 = new Product();
        product1.setName("Product1");
        product1.setPrice(10L);
        product1.setBrand("Brand1");
        product1.setCategory(blender);
        product1.setDescription("Description1");
        product1.setQuantity(100);
        product1.setBought(50);
        product1.setImgPath("path/to/image1");
        productList.add(product1);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(20L);
        product2.setBrand("Brand2");
        product2.setCategory(blender);
        product2.setDescription("Description2");
        product2.setQuantity(200);
        product2.setBought(100);
        product2.setImgPath("path/to/image2");
        productList.add(product2);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setPrice(30L);
        product3.setBrand("Brand2");
        product3.setCategory(blender);
        product3.setDescription("Description3");
        product3.setQuantity(300);
        product3.setBought(150);
        product3.setImgPath("path/to/image3");
        productList.add(product3);

        productDAO.saveCollection(productList);

        BlenderAttributes ba1 = new BlenderAttributes();
        ba1.setCapacity(111.);
        ba1.setProduct(product1);
        ba1.setHasDisplay(true);
        ba1.setHasTimer(true);
        ba1.setPowerOutput(111);
        ba1.setNumberOfSpeeds(111);
        blenderList.add(ba1);

        BlenderAttributes ba2 = new BlenderAttributes();
        ba2.setCapacity(222.);
        ba2.setProduct(product2);
        ba2.setHasDisplay(false);
        ba2.setHasTimer(false);
        ba2.setPowerOutput(222);
        ba2.setNumberOfSpeeds(222);
        blenderList.add(ba2);

        BlenderAttributes ba3 = new BlenderAttributes();
        ba3.setCapacity(222.);
        ba3.setProduct(product3);
        ba3.setHasDisplay(false);
        ba3.setHasTimer(false);
        ba3.setPowerOutput(222);
        ba3.setNumberOfSpeeds(222);
        blenderList.add(ba3);

        blenderAttributesDAO.saveCollection(blenderList);
    }
}
