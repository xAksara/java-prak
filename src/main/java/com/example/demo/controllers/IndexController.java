package com.example.demo.controllers;


import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.User;
import com.example.demo.services.CustomDBService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Logger;

@Controller
public class IndexController {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    public static final Logger logger = Logger.getLogger(CustomDBService.class.getName());

    @GetMapping(value = {"/index", "/"})
    public String HomePage(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        logger.info("hello");
        return "index";
    }

    @PostMapping("/logout")
    public String postLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/index";
    }

    @GetMapping("/print_message")
    public String print_message(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        return "print_message";
    }

}