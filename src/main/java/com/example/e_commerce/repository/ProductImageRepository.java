package com.example.e_commerce.repository;

import com.example.e_commerce.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
    List<String> findByProductId(int productId);
}
