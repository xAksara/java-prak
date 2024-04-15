package com.example.demo.DAO.Impl;

import com.example.demo.DAO.PaymentMethodDAO;
import com.example.demo.models.PaymentMethod;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PaymentMethodDAOImpl extends CommonDAOImpl<PaymentMethod, Long> implements PaymentMethodDAO {
    public PaymentMethodDAOImpl() {
        super(PaymentMethod.class);
    }
}
