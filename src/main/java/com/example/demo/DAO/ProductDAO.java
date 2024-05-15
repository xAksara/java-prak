package com.example.demo.DAO;

import com.example.demo.models.Category;
import com.example.demo.models.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProductDAO extends CommonDAO<Product, Long> {
    Product findByName(String name);
    public Collection<Product> findByFilters(Map<String, String> filters);
    Product findById(Long id);
    List<Product> findByBrand(String brand);
    List<Product> findByCategory(Category category);
    List<Product> findByPriceGreaterThanEqual(Long price);
    List<Product> findByPriceLessThanEqual(Long price);
    List<Product> findByQuantityGreaterThanEqual(Integer quantity);
    List<Product> findByQuantityLessThanEqual(Integer quantity);
    List<Product> findAllByOrderByBoughtDesc();
}
