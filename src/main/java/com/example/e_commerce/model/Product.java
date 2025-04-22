package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String brand;
    private int price;
    private int inventory;
    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductImage> images;
}
