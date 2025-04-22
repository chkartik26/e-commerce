package com.example.e_commerce.repository;

import com.example.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Boolean existsByName(String name);
    List<Product> findByCategoryId(int id);
    List<Product> findByBrand(String name);
    List<Product> findByBrandAndCategoryId(String brand,int id);
    Product findByName(String name);
    List<Product> findByBrandAndName(String brand,String name);
}
