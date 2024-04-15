package com.example.demo.DAO;

import com.example.demo.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testFindByLogin() {
        User foundUser = userDAO.findByLogin("login1");
        assertNotNull(foundUser);
        assertEquals("login1", foundUser.getLogin());

        // Заодно протестим CommonDAO чтоб не писать отдельный класс
        foundUser.setLogin("1login");
        userDAO.update(foundUser);

        foundUser = userDAO.findByLogin("login1");
        assertNull(foundUser);

        //getAll()
        Collection<User> userList = userDAO.getAll();
        assertEquals(3, userList.size());
    }

    @Test
    public void testExistsByLogin() {
        boolean exists = userDAO.existsByLogin("login1");
        assertTrue(exists);

        exists = userDAO.existsByLogin("login1131231231");
        assertFalse(exists);
    }

    @Test
    public void testFindByLastName() {
        List<User> users = userDAO.findByLastName("lastName2");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findByLastName("lastName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByFirstName() {
        List<User> users = userDAO.findByFirstName("firstName1");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findByFirstName("firstName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindBySurname() {
        List<User> users = userDAO.findBySurname("surname1");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findBySurname("lastName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByPhone() {
        List<User> users = userDAO.findByPhone("1234567890");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findByPhone("lastName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByEmail() {
        List<User> users = userDAO.findByEmail("user1@example.com");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findByEmail("lastName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testFindByAddress() {
        List<User> users = userDAO.findByAddress("123 Main Street, City, Country");
        assertNotNull(users);
        assertFalse(users.isEmpty());

        users = userDAO.findByAddress("lastName124142");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }


    @Test
    public void testFindAllAdmins() {
        List<User> admins = userDAO.findAllAdmins();
        assertNotNull(admins);
        assertFalse(admins.isEmpty());
        assertTrue(admins.stream().allMatch(User::isAdmin));
        assertEquals(1, admins.size());
    }

    @BeforeEach
    void beforeEach() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
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
        user3.setAdmin(true);
        UserList.add(user3);


        userDAO.saveCollection(UserList);

    }
}
