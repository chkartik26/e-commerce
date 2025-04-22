package com.example.e_commerce.service.cart;

import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.CartItems;
import com.example.e_commerce.model.Users;
import com.example.e_commerce.repository.CartItemRepository;
import com.example.e_commerce.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService{

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartitemRepository;
   // @Override
   // public List<CartItems> getCart(int id) {
//        Cart cart=cartRepository.findByUserId(id);
//        List<CartItems> items=cartitemRepository.findByCartId(id);
//        cart.updateTotalAmount(items);
//        return cartRepository.save(cart);
    //    List<CartItems> cartItems=cartitemRepository.findByCartId(id);
        //Cart cart=cartRepository.findById(id).orElseThrow(()-> new ResourceNotFound("jbv"));
       // System.out.println(cart);
       // return cartRepository.findById(id).orElseThrow();
  //      return cartItems;
//    }

    @Transactional
    @Override
    public void clearCart(int userId) {
        Cart cart=cartRepository.findByUserId(userId);
        cart.setTotalAmount(0);
        cartitemRepository.deleteByCartId(cart.getId());
        cartRepository.save(cart);
    }

    @Override
    public Cart initializeNewCart(Users user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
    public void setTotalAmount(int cartId,List<CartItems> cartItems){
        int totalAmount=0;
        for(CartItems items:cartItems){
            totalAmount +=items.getTotalPrice();
        }
        Cart cart=cartRepository.findById(cartId).orElseThrow();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
}
