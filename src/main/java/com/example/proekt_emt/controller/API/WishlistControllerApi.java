package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Frontend.ProductDTO;
import com.example.proekt_emt.model.Frontend.WishlistDTO;
import com.example.proekt_emt.model.Wishlist;
import com.example.proekt_emt.persistance.WishlistRepository;
import com.example.proekt_emt.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistControllerApi {

    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;

    public WishlistControllerApi(
            WishlistService wishlistService,
            WishlistRepository wishlistRepository
    ){
        this.wishlistService = wishlistService;
        this.wishlistRepository = wishlistRepository;
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

    @GetMapping("/{username}")
    public List<ProductDTO> getWishlistProductsFromUser(@PathVariable String username){
        List<ProductDTO> productDTOS = new ArrayList<>();

        this.wishlistService
                .findAllProductsForUser(username)
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;

    }

    @GetMapping("/separate")
    public List<ProductDTO> getWishlistProductsFromUser2(@RequestParam("user") String username){
        List<ProductDTO> productDTOS = new ArrayList<>();

        this.wishlistService
                .findAllProductsForUser(username)
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;

    }

}
