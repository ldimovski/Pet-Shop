package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO  implements Comparable<ProductDTO>{

    private Long id;

    private String name;

    private String description;


    private Float price;

    private Integer avalibleProducts;

    private Integer soldItems = 0;

    private Float Rating;

    private ManufacturerDTO manufacturer;

    private List<StoreLocation> storeLocations;

    private ItemType itemType;

    private ItemCategory itemCategory;

    private String image_html;

    private String imageBase64;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public ProductDTO (Product p){
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.avalibleProducts = p.getAvalibleProducts();
        this.soldItems = p.getSoldItems();
        this.Rating = p.getRating();
        this.itemType = p.getItemType();
        this.itemCategory = p.getItemCategory();
        this.storeLocations = new ArrayList<>(p.getStoreLocations());
        this.image_html = p.getImage_html();
        this.manufacturer = new ManufacturerDTO(p.getManufacturer());
        this.imageBase64 = p.getImageBase64();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Float getPrice() {
        return price;
    }

    public String getPriceMKD(){
        StringBuilder sb = new StringBuilder();
        sb.append(getPrice());
        sb.append("MKD");
        return sb.toString();
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAvalibleProducts() {
        return avalibleProducts;
    }

    public void setAvalibleProducts(Integer avalibleProducts) {
        this.avalibleProducts = avalibleProducts;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

    public ManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<StoreLocation> getStoreLocations() {
        return storeLocations;
    }

    public void setStoreLocations(List<StoreLocation> storeLocations) {
        this.storeLocations = storeLocations;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Integer getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(Integer soldItems) {
        this.soldItems = soldItems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_html() {
        return image_html;
    }

    public void setImage_html(String image_html) {
        this.image_html = image_html;
    }

    @Override
    public int compareTo(ProductDTO o) {
        return Float.compare(this.getRating(), o.getRating());
    }

}
