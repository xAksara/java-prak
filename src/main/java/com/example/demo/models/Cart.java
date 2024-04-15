package com.example.demo.models;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "carts")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cart implements CommonEntity<Cart.CartPK> {
    @EmbeddedId
    private CartPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

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
    public static class CartPK implements Serializable {
        private Long userId;
        private Long productId;
    }
}
