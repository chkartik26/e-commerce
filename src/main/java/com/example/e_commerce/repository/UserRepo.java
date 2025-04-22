package com.example.e_commerce.repository;

import com.example.e_commerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByEmail(String email);
    boolean existsByEmail(String email);
}
