package com.example.demo.DAO;

import com.example.demo.models.Cart;
import com.example.demo.models.OrderProduct;
import com.example.demo.models.Product;
import com.example.demo.models.Order;

import java.util.List;

public interface OrderProductDAO extends CommonDAO<OrderProduct, OrderProduct.OrderProductPK> {
    List<OrderProduct> findByOrder(Order user);
    List<OrderProduct> findByProduct(Product product);
}