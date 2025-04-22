package com.example.e_commerce.service.cart;

import com.example.e_commerce.dto.CartItemsDto;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Cart;
import com.example.e_commerce.model.CartItems;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.CartItemRepository;
import com.example.e_commerce.repository.CartRepository;
import com.example.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CartItemsService implements ICartItemService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @Override
    public CartItems addItemToCart(CartItemsDto cartItemsDto) {
        Product product = productRepository.findById(cartItemsDto.getProductId()).orElseThrow(() -> new ResourceNotFound("No product with this Id"));
        CartItems cartitem = new CartItems();
        if (product.getInventory() < cartItemsDto.getQuantity()) {
            throw new RuntimeException();
        }
        cartitem.setQuantity(cartItemsDto.getQuantity());
        cartitem.setUnitPrice(product.getPrice());
        cartitem.setTotalPrice();
        Cart cart = cartRepository.findByUserId(cartItemsDto.getUserId());
        cartitem.setProduct(product);
        cartitem.setCart(cart);
        product.setInventory(product.getInventory() - cartItemsDto.getQuantity());
        productRepository.save(product);
        return cartItemRepository.save(cartitem);

    }

    @Transactional
    @Override
    public void removeItemFromCart(int cartId, int productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFound("Cart not found"));

        cartItemRepository.deleteByCartIdAndProductId(cartId, productId);
        List<CartItems> items = cartItemRepository.findByCartId(cartId);
        cartService.setTotalAmount(cartId, items);

    }
    @Override
    public List<CartItems> getCartItemsByCartId(int cartId) {
        List<CartItems> cartItems = cartItemRepository.findByCartId(cartId);
        if (cartItems.isEmpty()) {
            throw new ResourceNotFound("No item found with this cart id");
        }
        cartService.setTotalAmount(cartId, cartItems);
        return cartItems;
    }
    @Override
    public void updateItemQuantity(int cartItemId, int change) {
        CartItems cartItems = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFound("No product found with this cart item id"));
        int finalQuantity = cartItems.getQuantity() + change;
        Product product = cartItems.getProduct();
        Cart cart = cartItems.getCart();
        if (finalQuantity == 0) {
            cartItemRepository.deleteByCartIdAndProductId(cart.getId(), product.getId());
        } else if (finalQuantity > product.getInventory()) {
            throw new RuntimeException();
        }
        cartItems.setQuantity(finalQuantity);
        cartItems.setTotalPrice();
        // Cart cart=cartRepository.findById(cartItems.getCartId());
        List<CartItems> items = cartItemRepository.findByCartId(cart.getId());
        this.setTotalAmount(cart.getId(),items);
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

