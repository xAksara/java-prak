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

import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class AdminController {

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
        return "admin";
    }
    @GetMapping("/admin/searchProduct")
    public String adminSearchProduct(Model model) {
        // Этап 1: Проверка прав пользователя - эту часть ты выполняешь
        logger.info("0");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        logger.info("2");
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
            return "adminSearchProduct";
        }
        List<String> brands = customDBService.getBrands();

//        Collection<Category> categories = categoryDAO.getAll();
        logger.info("3");
        String category = product.getCategory().getCategory();
        List<Attribute> attributes = customDBService.getProductAttributes(product);
//        List<Attribute> attributes = customDBService.getAttributesByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("attributes", attributes);
        model.addAttribute("brands", brands);
        model.addAttribute("product", product);
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
            return "adminSearchProduct";
        }
        productDAO.delete(product);
        //TODO
        model.addAttribute("errorMessage", "Успешно!");
        return "adminSearchProduct"; // Редирект на страницу административной панели
    }

    // Метод для обработки запроса на редактирование товара
    @GetMapping("/admin/searchProduct/editProduct")
    public String editProduct(@RequestParam Long productId,
                              @RequestParam String category,
                              @RequestParam String productName,
                              @RequestParam String productBrand,
                              @RequestParam Long productPrice,
                              @RequestParam Integer productQuantity,
                              @RequestParam String productDescription,
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
            model.addAttribute("message", "Ошибка! Нет товара с таким id");
            return "print_message";
        }
        product.setName(productName);
        product.setBrand(productBrand);
        product.setCategory(
                categoryDAO.getById(
                        categoryDAO.getIdByName(category)
                )
        );
        product.setPrice(productPrice);
        product.setQuantity(productQuantity);
        product.setDescription(productDescription);
        productDAO.save(product);

        boolean success = customDBService.setProductAttributes(category, product, attributes);

        if (!success) {
            model.addAttribute("message", "ERROR");
            return "print_message";
        }

//        model.addAttribute("product", product);
        //TODO error
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
        List<Attribute> attributes = customDBService.getAttributesByCategory(category);
        logger.info("adding" + category);
        model.addAttribute("brands", brands);
        model.addAttribute("attributes", attributes);
        model.addAttribute("category", category);
        model.addAttribute("selectedFilters", null);
        return "adminAddProduct";
    }

    @GetMapping("/admin/addProduct/add")
    public String addProduct(
            @RequestParam String productName,
            @RequestParam Long productPrice,
            @RequestParam String productBrand,
            @RequestParam String category,
            @RequestParam int productQuantity,
            @RequestParam String productDescription,
            @RequestParam Map<String, String> attributes,
            Model model) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        Product product = new Product();
        product.setName(productName);
        product.setBought(0);
        product.setBrand(productBrand);
        product.setPrice(productPrice);
        product.setCategory(categoryDAO.getById(categoryDAO.getIdByName(category)));
        product.setQuantity(productQuantity);
        product.setDescription(productDescription);
        product.setImgPath("path/to/img");

        productDAO.save(product);

        boolean success = customDBService.setProductAttributes(category, product, attributes);

        if (!success) {
            model.addAttribute("message", "ERROR");
            return "print_message";
        }
        return "admin";
    }


}
