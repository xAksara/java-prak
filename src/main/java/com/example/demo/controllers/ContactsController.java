package com.example.demo.controllers;


import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping(value = {"/contacts"})
    public String getIndex(Model model) {
        model.addAttribute("currentUser", getCurrentUser());
        return "contacts";
    }
}