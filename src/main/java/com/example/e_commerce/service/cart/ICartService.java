package com.example.e_commerce.service.cart;

import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.CartItems;
import com.example.e_commerce.model.Users;

import java.util.List;

public interface ICartService {
   // List<CartItems> getCart(int id);
    void clearCart(int id);
    Cart initializeNewCart(Users user);
}
