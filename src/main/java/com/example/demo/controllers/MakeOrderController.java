package com.example.demo.controllers;

import com.example.demo.DAO.*;
import com.example.demo.DAO.Impl.*;
import com.example.demo.models.*;
import com.example.demo.services.CustomDBService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class MakeOrderController {
    @Autowired
    CustomDBService customDBService = new CustomDBService();
    @Autowired
    UserDAO userDAO = new UserDAOImpl();
    @Autowired
    CartDAO cartDAO = new CartDAOImpl();
    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    OrderStatusDAO statusDAO = new OrderStatusDAOImpl();
    @Autowired
    OrderDAO orderDAO = new OrderDAOImpl();
    @Autowired
    OrderProductDAO orderProductDAO = new OrderProductDAOImpl();
    @Autowired
    PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAOImpl();

    private static final Logger logger = Logger.getLogger(MakeOrderController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping("/makeOrder")
    public String getMakeOrder(Model model) {
        User user = getCurrentUser();
        List<Cart> carts = cartDAO.findByUser(user);
        if (carts.isEmpty()) {
            model.addAttribute("message", "Ваша корзина пуста");
            model.addAttribute("currentUser", getCurrentUser());
            return "print_message";
        } else {
            Map<String, List<Long>> products = new HashMap<>();
            long summ = 0L;
            for (Cart cart : carts) {
                String productName = cart.getProduct().getName();
                Long quantity = (long)cart.getQuantity();
                long totalPrice = quantity * cart.getProduct().getPrice();
                summ += totalPrice;
                List<Long> productInfo = new ArrayList<>();
                productInfo.add(quantity);
                productInfo.add(totalPrice);

                products.put(productName, productInfo);
            }

            model.addAttribute("products", products);
            model.addAttribute("summ", summ);
        }
        model.addAttribute("user", user);
        model.addAttribute("currentUser", getCurrentUser());
        return "makeOrder";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(Model model, @RequestParam LocalDate deliveryDate, @RequestParam String paymentMethod, HttpSession session) {
        User user = getCurrentUser();
        List<Cart> carts = cartDAO.findByUser(user);

        if (carts.isEmpty()) {
            model.addAttribute("message", "Корзина пуста!");
            model.addAttribute("currentUser", getCurrentUser());
            return "print_message";
        }

        customDBService.confirmOrder(user, deliveryDate, paymentMethod, carts);
        model.addAttribute("message", "Спасибо за заказ!");
        model.addAttribute("currentUser", getCurrentUser());
        return "print_message";
    }
}
