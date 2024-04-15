package com.example.demo.DAO;

import com.example.demo.models.Category;
import com.example.demo.models.ToasterAttributes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategoryDAOTest {
    @Autowired
    CategoryDAO categoryDAO;
    @Test
    void testFindIdByName() {
        Long id = categoryDAO.getIdByName("phone");
        assertEquals(1, id);
    }
}
