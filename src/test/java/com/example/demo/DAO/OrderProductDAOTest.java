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

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderProductDAOTest {

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderStatusDAO orderStatusDAO;

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    public void testFindByOrder() {
        Order order = orderDAO.getById(1L);
        List<OrderProduct> foundOrderProducts = orderProductDAO.findByOrder(order);
        assertNotNull(foundOrderProducts);
        assertEquals(2, foundOrderProducts.size());
        assertEquals(order.getId(), foundOrderProducts.getFirst().getOrder().getId());

        for (OrderProduct op : foundOrderProducts) {
            orderProductDAO.delete(op);
        }
        foundOrderProducts = orderProductDAO.findByOrder(order);
        assertNotNull(foundOrderProducts);
        assertTrue(foundOrderProducts.isEmpty());
    }

    @Test
    public void testFindByProduct() {
        Product product = productDAO.findByName("Product3");
        List<OrderProduct> foundOrderProducts = orderProductDAO.findByProduct(product);
        assertNotNull(foundOrderProducts);
        assertEquals(1, foundOrderProducts.size());
        assertEquals(product.getId(), foundOrderProducts.getFirst().getProduct().getId());

        Product product2 = productDAO.findByName("Product909102941");
        foundOrderProducts = orderProductDAO.findByProduct(product2);
        assertNotNull(foundOrderProducts);
        assertTrue(foundOrderProducts.isEmpty());
    }


    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE prak.public.users RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE prak.public.orders RESTART IDENTITY CASCADE;").executeUpdate();
            session.getTransaction().commit();

        }

        List<User> UserList = new ArrayList<>();

        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        user1.setLastName("lastName1");
        user1.setFirstName("firstName1");
        user1.setSurname("surname1");
        user1.setBirthday(Date.valueOf("2001-01-01"));
        user1.setPhone("1234567890");
        user1.setEmail("user1@example.com");
        user1.setAddress("123 Main Street, City, Country");
        user1.setAdmin(false);
        UserList.add(user1);

        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        user2.setLastName("lastName2");
        user2.setFirstName("firstName2");
        user2.setSurname("surname2");
        user2.setBirthday(Date.valueOf("2002-02-02")); // Пример даты рождения
        user2.setPhone("2234567890");
        user2.setEmail("user2@example.com");
        user2.setAddress("223 Main Street, City, Country");
        user2.setAdmin(false);
        UserList.add(user2);

        User user3 = new User();
        user3.setLogin("login3");
        user3.setPassword("password3");
        user3.setLastName("lastName3");
        user3.setFirstName("firstName3");
        user3.setSurname("surname3");
        user3.setBirthday(Date.valueOf("2003-03-03")); // Пример даты рождения
        user3.setPhone("3234567890");
        user3.setEmail("user3@example.com");
        user3.setAddress("323 Main Street, City, Country");
        user3.setAdmin(false);
        UserList.add(user3);


        userDAO.saveCollection(UserList);
        
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

        List<Order> orderList = new ArrayList<>();

        OrderStatus status1 = orderStatusDAO.getById(1L);
        OrderStatus status2 = orderStatusDAO.getById(2L);
        OrderStatus status3 = orderStatusDAO.getById(3L);

        PaymentMethod method1 = paymentMethodDAO.getById(1L);
        PaymentMethod method2 = paymentMethodDAO.getById(2L);

        Order order1 = new Order();
        order1.setUser(user1);
        order1.setOrderDate(Date.valueOf("2001-01-01"));
        order1.setDeliveryDate(Date.valueOf("2001-01-02"));
        order1.setStatus(status1);
        order1.setPaymentMethod(method1);
        orderList.add(order1);

        Order order2 = new Order();
        order2.setUser(user2);
        order2.setOrderDate(Date.valueOf("2002-02-02"));
        order2.setDeliveryDate(Date.valueOf("2002-02-02"));
        order2.setStatus(status2);
        order2.setPaymentMethod(method2);
        orderList.add(order2);

        Order order3 = new Order();
        order3.setUser(user3);
        order3.setOrderDate(Date.valueOf("2003-03-03"));
        order3.setDeliveryDate(Date.valueOf("2003-03-02"));
        order3.setStatus(status3);
        order3.setPaymentMethod(method1);
        orderList.add(order3);

        orderDAO.saveCollection(orderList);

        List<OrderProduct> orderProductList = new ArrayList<>();

        OrderProduct op1 = new OrderProduct();
        OrderProduct.OrderProductPK pk1 = new OrderProduct.OrderProductPK();
        pk1.setOrderId(order1.getId());
        pk1.setProductId(product1.getId());
        op1.setProduct(product1);
        op1.setOrder(order1);
        op1.setId(pk1);
        op1.setQuantity(1);
        orderProductList.add(op1);

        OrderProduct op2 = new OrderProduct();
        OrderProduct.OrderProductPK pk2 = new OrderProduct.OrderProductPK();
        pk2.setOrderId(order2.getId());
        pk2.setProductId(product2.getId());
        op2.setProduct(product2);
        op2.setOrder(order2);
        op2.setId(pk2);
        op2.setQuantity(2);
        orderProductList.add(op2);

        OrderProduct op3 = new OrderProduct();
        OrderProduct.OrderProductPK pk3 = new OrderProduct.OrderProductPK();
        pk3.setOrderId(order1.getId());
        pk3.setProductId(product3.getId());
        op3.setProduct(product3);
        op3.setOrder(order1);
        op3.setId(pk3);
        op3.setQuantity(3);
        orderProductList.add(op3);


        orderProductDAO.saveCollection(orderProductList);
        
    }
}
