package com.example.demo.controllers;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.models.*;
import com.example.demo.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class OrdersController {

    @Autowired
    UserDAO userDAO = new UserDAOImpl();

    @Autowired
    CartDAO cartDAO = new CartDAOImpl();

    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();

    @Autowired
    OrderDAO orderDAO = new OrderDAOImpl();

    @Autowired
    OrderProductDAO orderProductDAO = new OrderProductDAOImpl();

    private static final Logger logger = Logger.getLogger(CartController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping("/orders")
    public String getOrders(Model model) {
        User user = getCurrentUser();
        List<Order> orders = orderDAO.findByUser(user);
        if (orders.isEmpty()) {
            model.addAttribute("message", "У вас небыло заказов");
        } else {
            model.addAttribute("orders", orders);
        }
        model.addAttribute("user", user);
        return "/orders";
    }
    @GetMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        logger.info("orders/id");
        Order order = orderDAO.getById(id);
        if (order == null) {
            model.addAttribute("message", "Заказ не найден");
            return "print_message";
        }

        User auth_user = getCurrentUser();
        if (!Objects.equals(order.getUser().getId(), auth_user.getId())) {
            model.addAttribute("message", "Вы не имеете доступа к этой странице.");
            return "print_message";
        }

        List<OrderProduct> orderProducts = orderProductDAO.findByOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("orderProducts", orderProducts);

        return "order_details";
    }
}
