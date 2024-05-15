package com.example.demo.DAO;

import com.example.demo.models.Cart;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.User;
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
public class CartDAOTest {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    public void testFindByUser() {
        User user = userDAO.findByLogin("login1");
        List<Cart> foundCarts = cartDAO.findByUser(user);
        assertNotNull(foundCarts);
        assertEquals(2, foundCarts.size());
        assertEquals(user.getId(), foundCarts.getFirst().getUser().getId());

        user = userDAO.findByLogin("login99999999");
        foundCarts = cartDAO.findByUser(user);
        assertNotNull(foundCarts);
        assertTrue(foundCarts.isEmpty());
    }

    @Test
    public void testFindByProduct() {
        Product product = productDAO.findByName("Product3");
        List<Cart> foundCarts = cartDAO.findByProduct(product);
        assertNotNull(foundCarts);
        assertEquals(1, foundCarts.size());
        assertEquals(product.getId(), foundCarts.getFirst().getProduct().getId());

        product = productDAO.findByName("Product999999");
        foundCarts = cartDAO.findByProduct(product);
        assertNotNull(foundCarts);
        assertTrue(foundCarts.isEmpty());
    }


    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE prak.public.carts RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE prak.public.products RESTART IDENTITY CASCADE;").executeUpdate();
            session.createNativeQuery("TRUNCATE TABLE prak.public.users RESTART IDENTITY CASCADE;").executeUpdate();
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

        List<Cart> cartList = new ArrayList<>();

        Cart cart1 = new Cart();
        Cart.CartPK pk1 = new Cart.CartPK();
        pk1.setUserId(user1.getId());
        pk1.setProductId(product1.getId());
        cart1.setProduct(product1);
        cart1.setUser(user1);
        cart1.setId(pk1);
        cart1.setQuantity(1);
        cartList.add(cart1);

        Cart cart2 = new Cart();
        Cart.CartPK pk2 = new Cart.CartPK();
        pk2.setUserId(user2.getId());
        pk2.setProductId(product2.getId());
        cart2.setId(pk2);
        cart2.setProduct(product2);
        cart2.setUser(user2);
        cart2.setQuantity(2);
        cartList.add(cart2);

        Cart cart3 = new Cart();
        Cart.CartPK pk3 = new Cart.CartPK();
        pk3.setUserId(user1.getId());
        pk3.setProductId(product3.getId());
        cart3.setUser(user1);
        cart3.setProduct(product3);
        cart3.setId(pk3);
        cart3.setQuantity(10);
        cartList.add(cart3);

        cartDAO.saveCollection(cartList);
    }
}
