package com.example.e_commerce.controller;

import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.order.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrdersService orderService;
    @PostMapping("/placeOrder/{cartId}")
    public ResponseEntity<?> placingOrder(@PathVariable int cartId){
        try{
            return new ResponseEntity<>(orderService.placeOrder(cartId), HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("order not placed"));
        }
    }

    @GetMapping("/getOrders/{userId}")
    public ResponseEntity<?> getOrder(@PathVariable int userId){
        try{
            return new ResponseEntity<>(orderService.allOrders(userId),HttpStatus.OK);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new  ApiResponse("No user found with this id"));
        }
    }
}
