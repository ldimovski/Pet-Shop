package com.example.proekt_emt.model;

import java.time.LocalDate;
import java.util.List;

public class Container {
    private ShoppingCart shoppingCart;
    private String price;
    private List<Item> items;
    private String address;
    private String endDateString;
    private LocalDate endDateLd;


    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public LocalDate getEndDateLd() {
        return endDateLd;
    }

    public void setEndDateLd(LocalDate endDateLd) {
        this.endDateLd = endDateLd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
