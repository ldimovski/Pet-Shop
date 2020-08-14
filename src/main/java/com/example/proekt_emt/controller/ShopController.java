package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.DealOfTheDay;
import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.service.DealOfTheDayService;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final DealOfTheDayService dealOfTheDayService;

    public ShopController(ProductService productService,
                          ManufacturerService manufacturerService,
                          DealOfTheDayService dealOfTheDayService){
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.dealOfTheDayService = dealOfTheDayService;
    }

    @GetMapping
    public String getShopPage(Model model){
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);

        /*
        Long l = new Long(1);
        Product p = this.productService.findById(l);
        DealOfTheDay d = new DealOfTheDay();
        d.setProduct(p);
        d.setActive(true);
        d.setDiscount(30);
        d.setEndDate(LocalDateTime.of(2020,10,12,12,12,12));
        this.dealOfTheDayService.save(d);*/

/*
        for(int i=0; i<3; i++)
        {
            Long l = new Long(3);
            Manufacturer m = this.manufacturerService.findById(l);
            Product p = new Product();
            p.setAvalibleProducts(10);
            p.setItemCategory(ItemCategory.DOG);
            p.setItemType(ItemType.FOOD);
            p.setManufacturer(m);
            p.setName("Purina Neurocare 3kg");
            p.setPrice((float)100);
            p.setSoldItems(0);
            this.productService.saveProduct(p);
        }
*/

        return "shop";

    }

    @GetMapping("/new")
    public String getNew(){
        return "newProduct";
    }
}
