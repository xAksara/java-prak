package com.example.demo.DAO;

import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import com.example.demo.models.User;

import java.util.List;

public interface CartDAO extends CommonDAO<Cart, Cart.CartPK> {
    List<Cart> findByUser(User user);
    List<Cart> findByProduct(Product product);
}