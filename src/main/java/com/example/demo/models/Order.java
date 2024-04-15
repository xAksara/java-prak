package com.example.demo.models;

import javax.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;

}
