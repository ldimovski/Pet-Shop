package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.*;
import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.example.proekt_emt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final DealOfTheDayService dealOfTheDayService;
    private final StoreLocationService storeLocationService;
    private final ShoppingCartService shoppingCartService;
    private final AuthService authService;
    public ShopController(ProductService productService,
                          ManufacturerService manufacturerService,
                          DealOfTheDayService dealOfTheDayService,
                          StoreLocationService storeLocationService,
                          ShoppingCartService shoppingCartService,
                          AuthService authService){
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.dealOfTheDayService = dealOfTheDayService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.authService = authService;
    }

    @GetMapping
    public String getShopPage(Model model){
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);

        return "shop";

    }

}
