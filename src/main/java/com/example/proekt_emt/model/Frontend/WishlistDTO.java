package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.model.Wishlist;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDTO {

    private Long id;

    private User user;

    private List<ProductDTO> products;

    public WishlistDTO(Wishlist wishlist){
        this.id = wishlist.getId();
        this.user = wishlist.getUser();
        this.products = new ArrayList<>();
        wishlist.getProducts()
                .stream()
                .forEach(p -> {
                    this.products.add(new ProductDTO(p));
                });
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
