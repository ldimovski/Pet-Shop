package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Enumerations.CartStatus;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.model.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {
    private Long id;

    private LocalDateTime createDate = LocalDateTime.now();
    private String endDate;

    private User user;

    private CartStatus status = CartStatus.CREATED;

    private String country;
    private String city;
    private String address;

    private Float price;

    private Integer discount;

    private List<ItemDTO> items;

    public ShoppingCartDTO(ShoppingCart sc, List<Item> items){
        this.items = new ArrayList<>();
        this.id = sc.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(sc.getEndDate() != null){
            this.endDate = sc.getEndDate().format(formatter);
        }
        this.createDate = sc.getCreateDate();
//        this.endDate = sc.getEndDate();
        this.user = sc.getUser();
        this.status = sc.getStatus();
        this.country = sc.getCountry();
        this.city = sc.getCity();
        this.address = sc.getAddress();
        this.price = sc.getPrice();
        for (Item item :
                items) {
            this.items.add(new ItemDTO(item));
        }
        this.discount = sc.getDiscount();

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

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCountry() {
        return country;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
