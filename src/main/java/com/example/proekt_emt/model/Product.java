package com.example.proekt_emt.model;

import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.sun.istack.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements Comparable<Product>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Lob
    private String imageBase64;

    private String description;

    private String image_html;


    @NotNull
    @Min(value = 0,message = "Price must be bigger than 0")
    private Float price;

    @NotNull
    @Min(value = 0,message = "Price must be bigger than 0")
    private Integer avalibleProducts;

    private Integer soldItems = 0;

    @Max(value = 5, message = "Rating can not be more than 5")
    private Float Rating;

    @NotNull
    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToMany
    private List<StoreLocation> storeLocations;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemType itemType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Wishlist> wishlists;

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> products) {
        this.wishlists = products;
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

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public Float getPrice() {
        return price;
    }

    public String getPriceDollar(){
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
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
    public int compareTo(Product o) {
        return Float.compare(this.getRating(), o.getRating());
    }
}
