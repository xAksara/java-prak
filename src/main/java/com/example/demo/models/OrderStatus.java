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
@Table(name = "order_statuses")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderStatus implements CommonEntity<Long> {
    @Id
    @Column(name = "status_id")
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;
}
