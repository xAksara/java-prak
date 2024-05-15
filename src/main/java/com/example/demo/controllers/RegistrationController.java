package com.example.demo.controllers;

import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class RegistrationController {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();

    private static final Logger logger = Logger.getLogger(MakeOrderController.class.getName());

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationUser(@RequestParam String login,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model)
    {
        logger.info("hello");
        User user = userDAO.findByLogin(login);

        if (user != null) {
            model.addAttribute("errorMessage", "Такой логин уже занят");
            return "registration";
        }
        if (!Objects.equals(password, confirmPassword)) {
            model.addAttribute("errorMessage", "Пароли не совпадают");
            return "registration";
        }
        user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userDAO.save(user);
        return "redirect:/index";
    }
}