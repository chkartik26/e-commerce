package com.example.e_commerce.service.order;

import com.example.e_commerce.dto.OrdersDto;
import com.example.e_commerce.model.Orders;

import java.util.List;

public interface IOrderService {

    Orders placeOrder(int userId);
//    Orders getOrder(int orderId);
    List<Orders> allOrders(int userId);
}
