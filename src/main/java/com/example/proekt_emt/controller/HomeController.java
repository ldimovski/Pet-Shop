package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.DealOfTheDay;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.AuthService;
import com.example.proekt_emt.service.DealOfTheDayService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final ProductService productService;
    private final DealOfTheDayService dealOfTheDayService;
    private final AuthService authService;

    public HomeController(ProductService productService,
                          DealOfTheDayService dealOfTheDayService,
                          AuthService authService){
        this.productService = productService;
        this.dealOfTheDayService = dealOfTheDayService;
        this.authService = authService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        List<Product> products = this.productService.getBestProducts();
        model.addAttribute("products", products);


        return "index";

        //DealOfTheDay d = this.dealOfTheDayService.findFirstByActive(true);
        //model.addAttribute("deal", d);

    }
}
