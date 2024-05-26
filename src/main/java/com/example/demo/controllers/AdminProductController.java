package com.example.demo.controllers;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.classes.Attribute;
import com.example.demo.models.*;
import com.example.demo.services.CustomDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Attr;

import java.util.*;
import java.util.logging.Logger;

@Controller
public class AdminProductController {

    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CartDAO cartDAO = new CartDAOImpl();
    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    CategoryDAO categoryDAO = new CategoryDAOImpl();
    @Autowired
    BlenderAttributesDAO blenderAttributesDAO = new BlenderAttributesDAOImpl();
    @Autowired
    PhoneAttributesDAO phoneAttributesDAO = new PhoneAttributesDAOImpl();
    @Autowired
    ToasterAttributesDAO toasterAttributesDAO = new ToasterAttributesDAOImpl();
    @Autowired
    CustomDBService customDBService = new CustomDBService();

    private static final Logger logger = Logger.getLogger(CartController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping("/admin")
    public String adminPage(Model model) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        model.addAttribute("currentUser", getCurrentUser());
        return "admin";
    }
    @GetMapping("/admin/searchProduct")
    public String adminSearchProduct(Model model) {
        logger.info("0");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        logger.info("2");
        model.addAttribute("currentUser", getCurrentUser());
        return "adminSearchProduct";
    }

    @GetMapping("/admin/searchProduct/search")
    public String searchProductById(@RequestParam Long productId, Model model) {
        logger.info("1");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        Product product = productDAO.getById(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "Товар не найден");
            model.addAttribute("currentUser", getCurrentUser());

            return "adminSearchProduct";
        }
        List<String> brands = customDBService.getBrands();

//        Collection<Category> categories = categoryDAO.getAll();
        logger.info("3");
        String category = product.getCategory().getCategory();
        List<Attribute> attributes = customDBService.getAllProductAttributes(product);
//        List<Attribute> attributes = customDBService.getAttributesByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("attributes", attributes);
        model.addAttribute("brands", brands);
        model.addAttribute("product", product);
        model.addAttribute("currentUser", getCurrentUser());

        return "adminSearchProduct";
    }

    @GetMapping("/admin/searchProduct/deleteProduct")
    public String deleteProduct(@RequestParam Long productId, Model model) {
        logger.info("delete");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        Product product = productDAO.getById(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "Товар не найден");
            model.addAttribute("currentUser", getCurrentUser());

            return "adminSearchProduct";
        }
        productDAO.delete(product);
        //TODO
        model.addAttribute("errorMessage", "Успешно!");
        model.addAttribute("currentUser", getCurrentUser());

        return "adminSearchProduct"; // Редирект на страницу административной панели
    }

    @GetMapping("/admin/searchProduct/editProduct")
    public String editProduct(@RequestParam Long productId,
                              @RequestParam String category,
                              @RequestParam Map<String, String> attributes,
                              Model model)
    {
        logger.info("edit");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        Product product = productDAO.getById(productId);
        if (product == null) {
            model.addAttribute("currentUser", getCurrentUser());


            model.addAttribute("message", "Ошибка! Нет товара с таким id");
            return "print_message";
        }
//        product.setName(productName);
//        product.setBrand(productBrand);
//        product.setCategory(
//                categoryDAO.getById(
//                        categoryDAO.getIdByName(category)
//                )
//        );
//        product.setPrice(productPrice);
//        product.setQuantity(productQuantity);
//        product.setDescription(productDescription);
//        productDAO.save(product);

        boolean success = customDBService.saveProductWithAttributes(category, product, attributes);

        if (!success) {
            model.addAttribute("message", "ERROR");
            model.addAttribute("currentUser", getCurrentUser());


            return "print_message";

        }

//        model.addAttribute("product", product);
        //TODO error
        model.addAttribute("currentUser", getCurrentUser());

        model.addAttribute("errorMessage", "Успех!");
        return "adminSearchProduct";
    }

    @GetMapping("/admin/choseCategory")
    public String choseCategory(Model model) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        List<String> categories = customDBService.getCategories();
        model.addAttribute("currentUser", getCurrentUser());

        model.addAttribute("categories", categories);
        return "adminChoseCategory";
    }

    @GetMapping("/admin/addProduct")
    public String addProduct(Model model, @RequestParam String category) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        List<String> brands = customDBService.getBrands();
        List<Attribute> attributes = customDBService.getEmptyProductAttributes(category);
        logger.info("adding" + category);
        for (Attribute a : attributes) {
            logger.info(a.getName());
        }
        model.addAttribute("brands", brands);
        model.addAttribute("attributes", attributes);
        model.addAttribute("category", category);
        model.addAttribute("selectedFilters", null);
        model.addAttribute("currentUser", getCurrentUser());

        return "adminAddProduct";
    }

    @PostMapping("/admin/addProduct/add")
    public String addProduct(
            @RequestParam String category,
            @RequestParam Map<String, String> attributes,
            Model model) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        Product product = new Product();
        boolean success = customDBService.saveProductWithAttributes(category, product, attributes);

        if (!success) {
            model.addAttribute("message", "ERROR");
            model.addAttribute("currentUser", getCurrentUser());

            return "print_message";
        }
        model.addAttribute("currentUser", getCurrentUser());

        return "admin";
    }


}
