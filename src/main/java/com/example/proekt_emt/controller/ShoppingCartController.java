package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final AuthService authService;
    private final StoreLocationService storeLocationService;
    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;

    public ShoppingCartController(ProductService productService,
                                  ManufacturerService manufacturerServic,
                                  AuthService authService,
                                  StoreLocationService storeLocationService,
                                  ShoppingCartService shoppingCartService,
                                  ItemService itemService){
        this.productService = productService;
        this.manufacturerService = manufacturerServic;
        this.authService = authService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
    }

    @GetMapping
    public String getNew(){
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addProductToShoppingCart(@PathVariable Long id){
        try{
            Item item = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), id);
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getLocalizedMessage();
        }
        return "redirect:/shop";
    }

}
