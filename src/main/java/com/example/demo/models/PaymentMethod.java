package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentMethod implements CommonEntity<Long> {
    @Id
    @Column(name = "method_id")
    private Long id;

    @Column(name = "method", nullable = false)
    private String method;
}
