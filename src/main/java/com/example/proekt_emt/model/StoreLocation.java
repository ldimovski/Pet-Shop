package com.example.proekt_emt.model;

import com.example.proekt_emt.service.ProductService;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "storeLocations")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String location;

    @NotNull
    private String name;

    private String workingTime;

    private boolean works;

    private String number;

    @ManyToMany
    private List<Product> products;

    public String getNumber() {
        return number;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public boolean isWorks() {
        return works;
    }

    public void setWorks(boolean works) {
        this.works = works;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean checkProduct (List<StoreLocation> locations){
        for (StoreLocation storeLocation: locations ) {
            if(storeLocation.getId().equals(getId())){
                return true;
            }
        }
        return false;
    }
}
