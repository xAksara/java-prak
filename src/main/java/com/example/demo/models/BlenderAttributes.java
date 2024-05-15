package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attributes_blender")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlenderAttributes implements CommonEntity<Long> {
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "capacity", columnDefinition = "DECIMAL(8,2)")
    private Double capacity;

    @Column(name = "has_timer")
    private Boolean hasTimer;

    @Column(name = "power_output")
    private Integer powerOutput;

    @Column(name = "number_of_speeds")
    private Integer numberOfSpeeds;

    @Column(name = "has_display")
    private Boolean hasDisplay;
}
