package com.example.demo.controllers;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.classes.Attribute;
import com.example.demo.models.*;
import com.example.demo.services.CustomDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class AdminUsersController {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CartDAO cartDAO = new CartDAOImpl();
    @Autowired
    OrderDAO orderDAO = new OrderDAOImpl();
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

    public static final Logger logger = Logger.getLogger(CustomDBService.class.getName());
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping("/admin/searchUser")
    public String adminSearchUser(Model model) {
        // Этап 1: Проверка прав пользователя - эту часть ты выполняешь
        logger.info("0");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        logger.info("searchUser");
        model.addAttribute("currentUser", getCurrentUser());
        return "adminSearchUser";
    }

    @GetMapping("/admin/searchUser/search")
    public String adminSearchUser(Model model, @RequestParam(required = false) Long userId, @RequestParam(required = false) String userLogin) {
        logger.info("0");
        model.addAttribute("currentUser", getCurrentUser());
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        logger.info("searchUser search");
        if (userId == null && userLogin == null) {
            model.addAttribute("message", "Введите id или login пользователя");
            return "print_message";
        }
        User user = null;
        if (userId != null) {
            user = userDAO.getById(userId);
        }
        else {
            user = userDAO.findByLogin(userLogin);
        }
        if (user == null) {
            model.addAttribute("message", "Неверный id или логин");
            model.addAttribute("user", getCurrentUser());
            return "print_message";
        }
        Collection<Order> userOrders = orderDAO.findByUser(user);
        List<Attribute> attributes = customDBService.getUserAttributes(user);
        model.addAttribute("user", user);
        model.addAttribute("attributes", attributes);
        model.addAttribute("userOrders", userOrders);
        return "adminSearchUser";
    }

    @PostMapping("/admin/searchUser/editUser")
    public String editUser(@RequestParam Long userId, @RequestParam Map<String, String> attributes, Model model) {
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "redirect:/print_message";
        }
        User user = userDAO.getById(userId);
        if (user == null) {
            model.addAttribute("message", "Ошибка! Нет юзера с таким id!");
            return "print_message";
        }
//        user.setPassword(userPassword);
//        user.setLastName(userLastName);
//        user.setFirstName(userFirstName);
//        user.setSurname(userSurname);
//        if (userBirthday != null && !userBirthday.isEmpty()) {
//            user.setBirthday(Date.valueOf(userBirthday));
//        }
//        user.setPhone(userPhone);
//        user.setEmail(userEmail);
//        user.setAddress(userAddress);
//        user.setAdmin(userIsAdmin);
        for (String a: attributes.keySet()) {
            logger.info(a + ": " + attributes.get(a));
        }

        customDBService.saveUserWithAttributes(user, attributes);
        List<Attribute> newAttributes = customDBService.getUserAttributes(user);
        model.addAttribute("message", "User updated successfully");
        Collection<Order> userOrders = orderDAO.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("attributes", newAttributes);
        model.addAttribute("userOrders", userOrders);
        model.addAttribute("currentUser", getCurrentUser());

        return "adminSearchUser";
    }

    @PostMapping("/admin/searchUser/deleteUser")
    public String deleteUser(@RequestParam Long userId, Model model) {
        logger.info("delete");
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        User user = userDAO.getById(userId);
        if (user == null) {
            model.addAttribute("errorMessage", "Пользователь не найден");
            model.addAttribute("currentUser", getCurrentUser());

            return "adminSearchUser";
        }
        userDAO.delete(user);
        //TODO
        model.addAttribute("errorMessage", "Успешно!");
        model.addAttribute("currentUser", getCurrentUser());

        return "adminSearchUser";
    }

    @GetMapping("/admin/addUser")
    public String addUserPage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        List<Attribute> attributes = customDBService.getEmptyUserAttributes();
        model.addAttribute("attributes", attributes);
        return "adminAddUser";
    }

    @PostMapping("/admin/addUser/add")
    public String addUser(Model model, @RequestParam Map<String, String> attributes) {
        model.addAttribute("currentUser", getCurrentUser());
        if (!getCurrentUser().isAdmin()) {
            model.addAttribute("message", "У вас нет доступа к этой странице");
            return "print_message";
        }
        User user = new User();
        boolean success = customDBService.saveUserWithAttributes(user, attributes);

        if (!success) {
            model.addAttribute("message", "ERROR");
            model.addAttribute("currentUser", getCurrentUser());
            return "print_message";
        }
        model.addAttribute("attributes", customDBService.getUserAttributes(user));
        return "adminSearchUser";
//        return "adminAddUser";
    }






}
