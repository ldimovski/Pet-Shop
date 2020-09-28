package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final AuthService authService;
    private final StoreLocationService storeLocationService;
    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;
    private final CouponService couponService;
    private final WishlistService wishlistService;

    public WishlistController(ProductService productService,
                                  ManufacturerService manufacturerServic,
                                  AuthService authService,
                                  StoreLocationService storeLocationService,
                                  ShoppingCartService shoppingCartService,
                                  ItemService itemService,
                                  CouponService couponService,
                              WishlistService wishlistService){
        this.productService = productService;
        this.manufacturerService = manufacturerServic;
        this.authService = authService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
        this.couponService = couponService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add/{productId}")
    public String addItemToWishlist (@PathVariable Long productId){
        User user;
        if(this.authService.getCurrentUser() != null){
            user = this.authService.getCurrentUser();
        }
        else {
            return "redirect:/shop?message=You are not logged in!";
        }


        try {

            this.wishlistService.addProductToWishList(user.getUsername(), productId);
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getLocalizedMessage();
        }
        return "redirect:/shop?message=Added product to wishlist!";
    }

    @GetMapping("/delete/{productId}")
    public String deleteItemFromWishlist (@PathVariable Long productId){
        User user;
        if(this.authService.getCurrentUser() != null){
            user = this.authService.getCurrentUser();
        }
        else {
            return "redirect:/shop?message=You are not logged in!";
        }

        try {
            this.wishlistService.deleteProductFromWishlist(user.getUsername(), productId);
        }
        catch (RuntimeException ex){
            return "redirect:/user?message=" + ex.getLocalizedMessage();
        }
        return "redirect:/user?message=Deleted item from wishlist!";
    }

    @GetMapping("/buy/{productId}")
    public String buyItemFromWishlist (@PathVariable Long productId){
        this.deleteItemFromWishlist(productId);
        this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), productId);
        return "redirect:/user?message=Added product to cart!";
    }
}
