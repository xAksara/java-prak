package com.example.demo.DAO;

import com.example.demo.models.Category;
import com.example.demo.models.Order;
import com.example.demo.models.Product;
import com.example.demo.models.User;

import java.util.Date;
import java.util.List;

public interface CategoryDAO extends CommonDAO<Category, Long> {
    Long getIdByName(String category);
}