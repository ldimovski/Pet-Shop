package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.service.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/store")
public class StoreLocationController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final StoreLocationService storeLocationService;
    private final AuthService authService;
    private final UserService userService;

    public StoreLocationController(ProductService productService,
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
    public String addStore(Model model){
        model.addAttribute("store", new StoreLocation());
        model.addAttribute("all", this.storeLocationService.findAll());
        model.addAttribute("msg", "New Store");
        return "newStore";
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String saveStore(@Valid StoreLocation storeLocation, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("store", new StoreLocation());
            return "newManufacturer";
        }

        if(storeLocation.getLocation().equals("")){
            return "redirect:/manufacturer/new?message=Location cannot be empty";
        }
        if(storeLocation.getName().equals("")){
            return "redirect:/manufacturer/new?message=Name cannot be emoty";
        }

        this.storeLocationService.save(storeLocation);
        return "redirect:/shop?message=Store saved";
    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    //@Secured("ROLE_MODERATOR")
    public String editStore(@PathVariable Long id, Model model){

        try{
            StoreLocation storeLocation = this.storeLocationService.findById(id);
            model.addAttribute("store", storeLocation);
            model.addAttribute("all", this.storeLocationService.findAll());
            model.addAttribute("msg", "New Store");
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getMessage();
        }

        return "newStore";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(Model model, @PathVariable Long id){
        try
        {
            this.storeLocationService.deleteById(id);
        }
        catch (RuntimeException ex){
            return "redirect:/shop"; //?message=" + ex.getMessage();
        }
        return "redirect:/shop?message=Store deleted";
    }
}
