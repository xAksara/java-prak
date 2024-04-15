package com.example.demo.models;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "product_categories")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Category implements CommonEntity<Long> {
    @Id
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;
}
