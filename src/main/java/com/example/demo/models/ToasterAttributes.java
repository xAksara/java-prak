package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attributes_toaster")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ToasterAttributes implements CommonEntity<Long> {
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "power_output")
    private Integer powerOutput;

    @Column(name = "number_of_slots")
    private Integer numberOfSlots;

    @Column(name = "has_display")
    private Boolean hasDisplay;

    @Column(name = "has_timer")
    private Boolean hasTimer;

    @Column(name = "has_bread_sensor")
    private Boolean hasBreadSensor;
}
