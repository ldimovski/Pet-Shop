package com.example.proekt_emt.controller;


import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService,
                             ManufacturerService manufacturerService){
        this.manufacturerService = manufacturerService;
        this.productService = productService;
    }

    @GetMapping("/new")
    public String addProductPage(){
        return "newProduct";
    }

    @GetMapping("/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        try {
            Product p = this.productService.findById(id);
            model.addAttribute("product", p);
            List<Product> relatedProducts = this.manufacturerService.getRelatedProducts(p.getManufacturer(), p);
            model.addAttribute("related", relatedProducts);
            return "product-single";
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message" + ex.getMessage();
        }
    }
}
