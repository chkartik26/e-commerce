package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;
    private int unitPrice;
    private int totalPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void setTotalPrice(){
        this.totalPrice = this.unitPrice * this.quantity;
    }
    public int getTotalPrice() {
        return this.totalPrice;
    }
}
