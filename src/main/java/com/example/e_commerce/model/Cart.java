package com.example.e_commerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalAmount;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<CartItems> items=new HashSet<>();

    public void updateTotalAmount(List<CartItems> itemsgtfd){
        for(CartItems item:itemsgtfd){
            this.totalAmount += item.getTotalPrice();
        }
    }
}
