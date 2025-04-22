package com.example.e_commerce.service.cart;

import com.example.e_commerce.dto.CartItemsDto;
import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.CartItems;

import java.util.List;

public interface ICartItemService {
    CartItems addItemToCart(CartItemsDto cartItems);
    void removeItemFromCart(int cartId,int productId);
    void updateItemQuantity(int cartItemId,int change);
    List<CartItems> getCartItemsByCartId(int cartId);
}
