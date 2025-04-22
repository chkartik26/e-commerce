package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(length = 512)
    String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
