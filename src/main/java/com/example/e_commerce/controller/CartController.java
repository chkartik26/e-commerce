package com.example.e_commerce.controller;

import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.CartItems;
import com.example.e_commerce.model.Users;
import com.example.e_commerce.repository.CartRepository;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.cart.CartService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @DeleteMapping("/deleteCart/{id}")
    public ResponseEntity<?> deletecart(@PathVariable int id){
     try{
         cartService.clearCart(id);
         return new ResponseEntity<>(HttpStatus.OK);
     }
     catch(ResourceNotFound e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart not found"));
        }
    }
}
