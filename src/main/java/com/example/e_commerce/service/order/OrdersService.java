package com.example.e_commerce.service.order;

import com.example.e_commerce.dto.OrdersDto;
import com.example.e_commerce.enums.OrderStatus;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.*;
import com.example.e_commerce.repository.*;
import com.example.e_commerce.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Service
public class OrdersService implements IOrderService{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepo userRepo;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    CartService cartService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Orders placeOrder(int userId) {
        Orders order=new Orders();

        Users user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFound("No user exists with this id"));
        System.out.println(user.getId());
        order.setUser(user);
        Cart cart=cartRepository.findByUserId(userId);
        order.setOrderDate(LocalDate.now());
        order.setOrderSum(cart.getTotalAmount());
        List<OrderItem> orderItems=cartItemToOrderItems(cart.getId());
        order.setOrderItems(orderItems);
        order.setOrderStatus(OrderStatus.PENDING);
        cartService.clearCart(userId);
        return orderRepository.save(order);
    }
//
//    @Override
//    public List<Orders> getOrder(int userId) {
//        return orderRepository.findByUserId(userId);
//        //.orElseThrow(()->new ResourceNotFound("No order found with this id"));
//    }


    @Override
    public List<Orders> allOrders(int userId) {
        List<Orders> orders=orderRepository.findByUserId(userId);
        return orders;
    }

    public List<OrderItem> cartItemToOrderItems(int cartId){
        List<CartItems> cartItems=cartItemRepository.findByCartId(cartId);
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItems cartItem:cartItems){
            OrderItem order= new OrderItem();
            order.setQuantity(cartItem.getQuantity());
            order.setPrice(cartItem.getUnitPrice());
            order.setTotalPrice(cartItem.getTotalPrice());
           // Product product=productRepository.findById(cartItem.getProduct());
            order.setProduct(cartItem.getProduct());
            orderItems.add(order);
        }
        return orderItems;
    }
}
