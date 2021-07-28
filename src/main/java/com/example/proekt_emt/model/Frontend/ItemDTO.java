package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.ShoppingCart;
import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class ItemDTO {
    private Long id;

    private ProductDTO product;

    private Integer quantity;


    public ItemDTO(Item item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
//        this.productId = item.getProduct().getId();
        this.product = new ProductDTO(item.getProduct());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public Float getTotalPrice(){
//        return this.product.getPrice() * quantity;
//    }
//
//    public String getTotalPriceUSD(){
//        return (this.product.getPrice() * quantity) + "MKD";
//    }


//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
}
