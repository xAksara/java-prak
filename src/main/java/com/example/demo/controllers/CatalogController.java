package com.example.demo.controllers;



import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.Impl.CartDAOImpl;
import com.example.demo.DAO.Impl.CategoryDAOImpl;
import com.example.demo.DAO.Impl.ProductDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.classes.Attribute;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.services.CustomDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.logging.Logger;

@Controller
public class CatalogController {
    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CartDAO cartDAO = new CartDAOImpl();
    @Autowired
    CustomDBService customDBService = new CustomDBService();

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }

    @GetMapping("/catalog")
    public String CatalogPage(Model model, @RequestParam String category, @RequestParam Map<String, String> filters) {
        Collection<Product> products;
        if (!filters.isEmpty()) {
            products = productDAO.findByFilters(filters);
        } else {
            products = productDAO.getAll();
        }

        for (String filter : filters.keySet()) {
            logger.info(filter + "    " + filters.get(filter));
        }
        List<String> brands = customDBService.getBrands();
        List<Attribute> attributes = customDBService.getEmptyAttributesByCategory(category);

        model.addAttribute("category", category);
        model.addAttribute("selectedFilters", filters);
        model.addAttribute("selectedSort", filters.get("sort"));
        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("attributes", attributes);

        return "catalog";
    }

    @GetMapping("catalog/choseCategory")
    public String ChoseCategory(Model model) {
        List<String> categories = customDBService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser", getCurrentUser());
        return "catalogChoseCategory";
    }
}
