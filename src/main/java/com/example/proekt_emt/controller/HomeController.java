package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Product;
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

    public HomeController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        List<Product> products = this.productService.getBestProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
