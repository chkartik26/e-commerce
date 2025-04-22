package com.example.e_commerce.repository;

import com.example.e_commerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    Cart findByUserId(int id);
}
