package com.zain_discoveries.com.zain_discoveries.clothing_website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private Cart cart;
    @ManyToOne
    private Product product;
    private int size;
    private int quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;
}
