package com.example.demo.DAO;

import com.example.demo.models.PaymentMethod;

public interface PaymentMethodDAO extends CommonDAO<PaymentMethod, Long> {
    public PaymentMethod findByMethod(String method);
}