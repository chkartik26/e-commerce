package com.example.e_commerce.repository;

import com.example.e_commerce.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItems,Integer> {
    void deleteByCartId(int id);
    void deleteByCartIdAndProductId(int cart_id,int product_id);
    List<CartItems> findByCartId(int cartId);
}
