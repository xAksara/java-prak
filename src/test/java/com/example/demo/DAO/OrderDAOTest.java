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
public class OrderDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderStatusDAO orderStatusDAO;

    @Autowired
    private PaymentMethodDAO paymentMethodDAO;

    @Test
    public void testFindByUser() {
        User user = userDAO.getById(1L);
        List<Order> orders = orderDAO.findByUser(user);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(o -> o.getUser().getId().equals(user.getId())));

        User user2 = userDAO.getById(111111L);
        orders = orderDAO.findByUser(user2);
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void testFindByOrderDate() {
        Date orderDate = orderDAO.getById(2L).getOrderDate();
        List<Order> orders = orderDAO.findByOrderDate(orderDate);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(o -> o.getOrderDate().equals(orderDate)));

        Date date2 = new Date(2100 - 1900, 1, 1);
        orders = orderDAO.findByOrderDate(date2);
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void testFindByDeliveryDate() {
        Date deliveryDate = orderDAO.getById(3L).getDeliveryDate();
        List<Order> orders = orderDAO.findByDeliveryDate(deliveryDate);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(o -> o.getDeliveryDate().equals(deliveryDate)));

        Date date2 = new Date(2100 - 1900, 1, 1);
        orders = orderDAO.findByDeliveryDate(date2);
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }
//
    @Test
    public void testFindByStatus() {
        OrderStatus status = orderStatusDAO.getById(1L);
        List<Order> orders = orderDAO.findByStatus(status);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(o -> o.getStatus().getId().equals(status.getId())));

        OrderStatus status2 = orderStatusDAO.getById(3L);
        orders = orderDAO.findByStatus(status2);
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }

    @Test
    public void testFindByPaymentMethod() {
        PaymentMethod paymentMethod = paymentMethodDAO.getById(1L);
        List<Order> orders = orderDAO.findByPaymentMethod(paymentMethod);
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertTrue(orders.stream().allMatch(o -> o.getPaymentMethod().getId().equals(paymentMethod.getId())));

        for (Order order : orders) {
            orderDAO.deleteById(order.getId());
        }

        orders = orderDAO.findByPaymentMethod(paymentMethod);
        assertNotNull(orders);
        assertTrue(orders.isEmpty());
    }



    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE prak.public.orders RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE prak.public.users RESTART IDENTITY CASCADE;").executeUpdate();
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
        order3.setStatus(status2);
        order3.setPaymentMethod(method1);
        orderList.add(order3);

        orderDAO.saveCollection(orderList);
    }

}
