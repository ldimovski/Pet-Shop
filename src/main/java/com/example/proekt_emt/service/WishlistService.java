package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.Wishlist;

import java.util.List;


public interface WishlistService {
    Wishlist findById(Long id);
    Wishlist findByUser(String username);
    List<Wishlist> findAll();
    void deleteById(Long id);
    Wishlist save(Wishlist wishlist);
    Wishlist addProductToWishList(String username ,Long productId);
    void deleteProductFromWishlist(String username, Long productId);
    List<Product> findAllProductsForUser(String username);
    //void deleteItemFromAll(Long productId);
}
