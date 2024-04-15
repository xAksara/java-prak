package com.example.demo.models;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "attributes_phone")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhoneAttributes implements CommonEntity<Long> {
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "screen_diagonal", columnDefinition = "DECIMAL(8,2)")
    private Double screenDiagonal;

    @Column(name = "processor", length = 255)
    private String processor;

    @Column(name = "memory_size")
    private Integer memorySize;

    @Column(name = "ram_size")
    private Integer ramSize;

    @Column(name = "screen_type", length = 255)
    private String screenType;
}
