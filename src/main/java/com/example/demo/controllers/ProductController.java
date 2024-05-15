package com.example.demo.controllers;



import com.example.demo.DAO.CartDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.Impl.CartDAOImpl;
import com.example.demo.DAO.Impl.CategoryDAOImpl;
import com.example.demo.DAO.Impl.ProductDAOImpl;
import com.example.demo.DAO.Impl.UserDAOImpl;
import com.example.demo.DAO.ProductDAO;
import com.example.demo.DAO.UserDAO;
import com.example.demo.models.Cart;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class ProductController {

    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Autowired
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Autowired
    UserDAO userDAO = new UserDAOImpl();

    @Autowired
    CartDAO cartDAO = new CartDAOImpl();

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());


    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDAO.findByLogin(authentication.getName());
    }

    @GetMapping("/product/{productId}")
    public String getProductPage(@PathVariable Long productId, Model model) {
        Product product = productDAO.getById(productId);

        Object attributes = switch (product.getCategory().getCategory()) {
            case "phone" -> product.getPhoneAttributes();
            case "toaster" -> product.getToasterAttributes();
            case "blender" -> product.getBlenderAttributes();
            default -> product.getBlenderAttributes();
        };

        Map<String, Object> attributeMap = new HashMap<>();
        if (attributes != null) {
            int skippedFields = 0;
            for (Field field : attributes.getClass().getDeclaredFields()) {
                if (skippedFields != 2) {
                    skippedFields++;
                    continue;
                }
                try {
                    field.setAccessible(true);
                    if (field.get(attributes) == null) {
                        continue;
                    }
                    attributeMap.put(field.getName(), field.get(attributes));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("attributes", attributeMap);

        return "product";
    }

//    @PostMapping("/product/{id}/add-to-cart")
//    public String addToCart(Model model, @RequestParam Long productId, @RequestParam int quantity, @PathVariable String id) {
//        User user = getCurrentUser();
////        if (user == null) {
////            return "redirect:/login";
////        }
//
//        List<Cart> carts = cartDAO.findByUser(user);
//
//
//        // Получаем товар по его идентификатору
//        Product product = productDAO.findById(productId);
//        if (product.getQuantity() < quantity) {
//            //TODO
//            logger.info("NET TOVARA");
//            model.addAttribute("errorMessage", "Нет требуемого количества товара");
//            return "redirect:/product/" + productId;
//        }
//
//        // Создаем или обновляем запись о товаре в корзине
//        Cart cart = null;
//        for (Cart c : carts) {
//            if (Objects.equals(c.getProduct().getId(), productId)) {
//                cart = c;
//                break;
//            }
//        }
//
//        if (cart == null) {
//            // Если товар еще не был добавлен в корзину, создаем новую запись
//            cart = new Cart();
//            Cart.CartPK pk = new Cart.CartPK();
//            pk.setUserId(user.getId());
//            pk.setProductId(product.getId());
//            cart.setProduct(product);
//            cart.setUser(user);
//            cart.setId(pk);
//            cart.setQuantity(quantity);
//            cartDAO.save(cart);
//        } else {
//            // Если товар уже есть в корзине, обновляем количество
//            cart.setQuantity(cart.getQuantity() + quantity);
//            cartDAO.update(cart);
//        }
//        product.setQuantity(product.getQuantity() - quantity);
//        productDAO.update(product);
//
//        return "redirect:/product/" + productId;
//    }

    @GetMapping("/product/{id}/addToCart")
    public String addToCart(Model model, @RequestParam(required = true) int quantity, @RequestParam(required = true) Long productId) {
        User user = getCurrentUser();
        List<Cart> carts = cartDAO.findByUser(user);
        Product product = productDAO.findById(productId);
        if (product.getQuantity() < quantity) {
            model.addAttribute("errorMessage", "Нет требуемого количества товара");
            return "redirect:/product/" + productId;
        }

        // Создаем или обновляем запись о товаре в корзине
        Cart cart = null;
        for (Cart c : carts) {
            if (Objects.equals(c.getProduct().getId(), productId)) {
                cart = c;
                break;
            }
        }

        if (cart == null) {
            cart = new Cart();
            Cart.CartPK pk = new Cart.CartPK();
            pk.setUserId(user.getId());
            pk.setProductId(product.getId());
            cart.setProduct(product);
            cart.setUser(user);
            cart.setId(pk);
            cart.setQuantity(quantity);
            cartDAO.save(cart);
        } else {
            cart.setQuantity(cart.getQuantity() + quantity);
            cartDAO.update(cart);
        }

        return "redirect:/product/" + productId;
    }
}
