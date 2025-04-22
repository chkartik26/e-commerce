package com.example.e_commerce.dto;

import com.example.e_commerce.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class OrdersDto {
    private LocalDate orderDate;
    private int orderSum;
    private OrderStatus orderStatus;
}
