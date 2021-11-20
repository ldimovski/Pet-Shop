package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Frontend.ProductDTO;
import com.example.proekt_emt.model.Frontend.WishlistDTO;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.model.Wishlist;
import com.example.proekt_emt.persistance.WishlistRepository;

import com.example.proekt_emt.service.WishlistService;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistControllerApi {

    private final WishlistService wishlistService;

    public WishlistControllerApi(
            WishlistService wishlistService
    ){
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<WishlistDTO> getAllWishlists(){
        List<WishlistDTO> wishlistDTOS = new ArrayList<>();

        this.wishlistService.findAll()
                .stream()
                .forEach(w -> {
                    wishlistDTOS.add(new WishlistDTO(w));
                });
        return wishlistDTOS;
    }

    @GetMapping("/personal")
    public List<ProductDTO> getWishlistProductsFromUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<ProductDTO> productDTOS = new ArrayList<>();
        this.wishlistService
                .findAllProductsForUser(userDetails.getUsername())
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;
    }

    @GetMapping("/add/{productId}")
    public void addProductToWishlist(@PathVariable Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        this.wishlistService.addProductToWishList(userDetails.getUsername(), productId);
    }

    @GetMapping("/remove/{productId}")
    public void removeProductToWishlist(@PathVariable Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        this.wishlistService.deleteProductFromWishlist(userDetails.getUsername(), productId);
    }


//    @GetMapping("/separate")
//    public List<ProductDTO> getWishlistProductsFromUser2(@RequestParam("user") String username){
//        List<ProductDTO> productDTOS = new ArrayList<>();
//
//        this.wishlistService
//                .findAllProductsForUser(username)
//                .stream()
//                .forEach(p -> {
//                    productDTOS.add(new ProductDTO(p));
//                });
//        return productDTOS;
//
//    }

}
