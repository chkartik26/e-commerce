package com.example.e_commerce.repository;

import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByUserId(int userId);
}
