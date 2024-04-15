package com.example.demo.models;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "orders_products")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderProduct implements CommonEntity<OrderProduct.OrderProductPK>{
    @EmbeddedId
    private OrderProductPK id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Getter
    @Setter
    @EqualsAndHashCode
    @Embeddable
    public static class OrderProductPK implements Serializable {
        private Long orderId;
        private Long productId;
    }
}
