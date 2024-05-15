package com.example.demo.controllers;

import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.Impl.CartDAOImpl;
import com.example.demo.DAO.Impl.ProductDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;
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
public class CartController {

    @Autowired
    UserDAO userDAO = new UserDAOImpl();

    @Autowired
    CartDAO cartDAO = new CartDAOImpl();

    @Autowired
    ProductDAO productDAO = new ProductDAOImpl();

    private static final Logger logger = Logger.getLogger(CartController.class.getName());

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }
    @GetMapping("/cart")
    public String getCart(Model model) {
        User user = getCurrentUser();
        List<Cart> carts = cartDAO.findByUser(user);
        if (carts.isEmpty()) {
            model.addAttribute("message", "Ваша корзина пуста");
        } else {
            Map<String, Integer> products = new HashMap<>();
            Map<String, Long> products_id = new HashMap<>();
            for (Cart cart : carts)
            {
                products.put(cart.getProduct().getName(), cart.getQuantity());
                products_id.put(cart.getProduct().getName(), cart.getProduct().getId());
            }
            model.addAttribute("products", products);
            model.addAttribute("products_id", products_id);
        }
        model.addAttribute("user", user);
        return "cart";
    }

    @GetMapping("/cart/delFromCart")
    public String delFromCart(Model model, @RequestParam(required = true) Long productId) {
        User user = getCurrentUser();
        List<Cart> carts = cartDAO.findByUser(user);
        for (Cart cart : carts)
        {
            if (Objects.equals(cart.getProduct().getId(), productId)) {
                cartDAO.delete(cart);
                break;
            }
        }
        return "redirect:/cart";
    }
}
