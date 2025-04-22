package com.example.e_commerce.repository;

import com.example.e_commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Category findByName(String name);
    Boolean existsById(int id);
    Boolean existsByName(String name);
}
