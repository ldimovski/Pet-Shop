package com.example.proekt_emt.controller;


import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final StoreLocationService storeLocationService;
    private final AuthService authService;
    private final UserService userService;

    public ProductController(ProductService productService,
                             ManufacturerService manufacturerService,
                             StoreLocationService storeLocationService,
                             AuthService authService,
                             UserService userService){
        this.manufacturerService = manufacturerService;
        this.productService = productService;
        this.storeLocationService = storeLocationService;
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/new")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    //@Secured("ROLE_MODERATOR")
    public String addProductPage(Model model){

        List<Product> products =this.productService.findAll();
        model.addAttribute("products", products);

        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("product", new Product());
        List<StoreLocation> storeLocations = this.storeLocationService.findAll();
        model.addAttribute("stores", storeLocations);
        model.addAttribute("categories", ItemCategory.values());
        model.addAttribute("types", ItemType.values());
        model.addAttribute("msg", "New Product");


        return "newProduct";
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String saveProduct(@Valid Product product, BindingResult bindingResult, @RequestParam MultipartFile image, Model model){
        if (bindingResult.hasErrors()){

            List<Product> products =this.productService.findAll();
            model.addAttribute("products", products);
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("product", new Product());
            List<StoreLocation> storeLocations = this.storeLocationService.findAll();
            model.addAttribute("stores", storeLocations);
            model.addAttribute("categories", ItemCategory.values());
            model.addAttribute("types", ItemType.values());
            return "newProduct";
        }

        if(product.getPrice() <= 0 ){
            return "redirect:/product/new?message=Price must be bigger than 0";
        }

        if(product.getRating() <= 0 || product.getRating() > 5){
            return "redirect:/product/new?message=Rating must be bigger than 0 and smaller than 5";
        }
        if(product.getAvalibleProducts() <= 0 ){
            return "redirect:/product/new?message=Avalible products must be bigger than 0";
        }
        if(product.getName().equals("")){
            return "redirect:/product/new?message=Name cannot be emoty";
        }


        try {
            this.productService.saveProduct(product, image);
        }
        catch (IOException e){
            e.printStackTrace();
            return "redirect:/product/new?message=" + e.getLocalizedMessage();
        }
        return "redirect:/shop?message=Product saved";
    }

    @GetMapping("/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        try {
            Product p = this.productService.findById(id);
            model.addAttribute("product", p);

            List<Product> relatedProducts = this.manufacturerService.getRelatedProducts(p.getManufacturer(), p);
            model.addAttribute("related", relatedProducts);

            List<StoreLocation> storeLocations = this.storeLocationService.findAll();
            model.addAttribute("stores", storeLocations);



            return "product-single";
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getMessage();
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(Model model, @PathVariable Long id){
        this.productService.deleteById(id);
        return "redirect:/shop?message=Product deleted";
    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    //@Secured("ROLE_MODERATOR")
    public String editProduct(@PathVariable Long id, Model model){
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);

        try{
            Product p = this.productService.findById(id);
            model.addAttribute("product", p);
            List<Product> products =this.productService.findAll();
            model.addAttribute("products", products);
            List<StoreLocation> storeLocations = this.storeLocationService.findAll();
            model.addAttribute("stores", storeLocations);
            model.addAttribute("categories", ItemCategory.values());
            model.addAttribute("types", ItemType.values());
            model.addAttribute("msg", "Edit Product");
            return "newProduct";
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getMessage();
        }

    }
}
