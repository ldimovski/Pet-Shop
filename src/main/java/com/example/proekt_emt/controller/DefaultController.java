package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.StoreLocationService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class DefaultController {

    private final StoreLocationService storeLocationService;
    private final ProductService productService;
    private final ManufacturerService manufacturerService;

    public DefaultController(StoreLocationService storeLocationService,
                             ProductService productService,
                             ManufacturerService manufacturerService){
        this.storeLocationService = storeLocationService;
        this.productService = productService;
        this.manufacturerService = manufacturerService;
    }


    @GetMapping
    public String defaultPage (Model model){
        return "redirect:index";
    }

    @GetMapping("/about")
    public String getAbout(Model model){
        model.addAttribute("customers", this.productService.getNumberSoldItems());
        model.addAttribute("branches", this.storeLocationService.findAll().size());
        model.addAttribute("partners", this.manufacturerService.findAll().size());
        return "about";
    }


    @GetMapping("/contact")
    public String getContactPage(Model model){
        List<StoreLocation> locations = this.storeLocationService.findAll();
        model.addAttribute("stores", locations);
        return "contact";
    }
}
