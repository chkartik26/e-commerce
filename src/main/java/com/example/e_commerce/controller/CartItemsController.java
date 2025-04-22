package com.example.e_commerce.controller;

import com.example.e_commerce.dto.CartItemsDto;
import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.CartItems;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.cart.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItems")
public class CartItemsController {

    @Autowired
    CartItemsService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<?> addCartItems(@RequestBody CartItemsDto cartitemsdto){
        try{
            cartItemService.addItemToCart(cartitemsdto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item is already added to cart"));
        }
    }

    @DeleteMapping("/remove/{productId}/{cartId}")
    public ResponseEntity<?> removeItem(@PathVariable int productId,@PathVariable int cartId){
        try{
            cartItemService.removeItemFromCart(cartId,productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(ResourceNotFound e){
            return new ResponseEntity<>("Item cannot be removed",HttpStatus.NOT_MODIFIED);
        }
    }
    @GetMapping("cartItemsById/{cartId}")
    public ResponseEntity<?> getItemsByCartId(@PathVariable int cartId){
        try {
            return new ResponseEntity<>(cartItemService.getCartItemsByCartId(cartId),HttpStatus.OK);
        }
        catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new  ApiResponse("No item found with this cart id"));
        }
    }

    @PutMapping("/updateItem/{id}/{change}")
    public ResponseEntity<?> updateItem(@RequestParam int cartItemId,@RequestParam int change){
        try{
            cartItemService.updateItemQuantity(cartItemId,change);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>("Quantity could not be modified",HttpStatus.NOT_MODIFIED);
        }
    }

}
