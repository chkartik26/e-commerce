package com.example.e_commerce.dto;

import com.example.e_commerce.model.Cart;
import lombok.Data;

@Data
public class CartItemsDto {

    private int quantity;
    private int  userId;
    private int productId;
}
