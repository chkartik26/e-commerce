package com.example.e_commerce.dto;

import com.example.e_commerce.model.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private String brand;
    private int price;
    private int inventory;
    private String description;
    private int  categoryId;
}
