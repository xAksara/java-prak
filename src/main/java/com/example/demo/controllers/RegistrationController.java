package com.example.demo.controllers;

import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.Impl.CartDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import com.example.demo.classes.Attribute;
import com.example.demo.models.User;
import com.example.demo.services.CustomDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class RegistrationController {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CustomDBService customDBService = new CustomDBService();
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }

    private static final Logger logger = Logger.getLogger(MakeOrderController.class.getName());

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        List<Attribute> attributes = customDBService.getEmptyUserAttributes();
        model.addAttribute("attributes", attributes);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@RequestParam Map<String, String> selectedAttributes,
                               @RequestParam String confirmPassword,
                               Model model)
    {
        logger.info("hello");
        List<Attribute> attributes = customDBService.getEmptyUserAttributes();
        User user = userDAO.findByLogin(selectedAttributes.get("userLogin"));
        model.addAttribute("currentUser", getCurrentUser());
        if (user != null) {
            model.addAttribute("attributes", attributes);
            model.addAttribute("errorMessage", "Такой логин уже занят");
            return "registration";
        }
        if (!Objects.equals(selectedAttributes.get("userPassword"), confirmPassword)) {
            model.addAttribute("errorMessage", "Пароли не совпадают");
            model.addAttribute("attributes", attributes);
            return "registration";
        }
        user = new User();
        boolean success = customDBService.saveUserWithAttributes(user, selectedAttributes);
        if (!success) {
            model.addAttribute("message", "ERROR");
            model.addAttribute("currentUser", getCurrentUser());
            return "print_message";
        }
//        userDAO.save(user);
        return "redirect:/index";
    }
}