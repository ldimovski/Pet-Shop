package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDTO {

    private Long id;

    private String name;

    private String location;

    private String description;

//    private List<ProductDTO> products;

    public ManufacturerDTO(Manufacturer manufacturer){
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
        this.location = manufacturer.getLocation();
        this.description = manufacturer.getDescription();
//        this.products = new ArrayList<>();
//        manufacturer.getProducts()
//                .stream()
//                .forEach(p -> {
//                    this.products.add(new ProductDTO(p));
//                });
    }

//    public List<ProductDTO> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<ProductDTO> products) {
//        this.products = products;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
