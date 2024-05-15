package com.example.demo.controllers;

import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.Impl.CartDAOImpl;
import com.example.demo.DAO.Impl.CategoryDAOImpl;
import com.example.demo.DAO.Impl.ProductDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
public class PersonalController {
    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Autowired
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Autowired
    UserDAO userDAO = new UserDAOImpl();

    @Autowired
    CartDAO cartDAO = new CartDAOImpl();

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }

    @GetMapping("/personal")
    public String getPersonal(Model model) {
        User user = getCurrentUser();
        model.addAttribute("email", user.getEmail());
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("address", user.getAddress());
        model.addAttribute("firstname", user.getFirstName());
        model.addAttribute("lastname", user.getLastName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("birthday", user.getBirthday());
        model.addAttribute("login", user.getLogin());
        return "/personal";
    }
}
