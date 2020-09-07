package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Enumerations.ItemCategory;
import com.example.proekt_emt.model.Enumerations.ItemType;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.service.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final StoreLocationService storeLocationService;
    private final AuthService authService;
    private final UserService userService;

    public ManufacturerController(ProductService productService,
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
    public String addManufacturerPage(Model model){
        model.addAttribute("manufacturer", new Manufacturer());
        model.addAttribute("all", this.manufacturerService.findAll());
        model.addAttribute("msg", "New Manufacturer");
        return "newManufacturer";
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    public String saveManufacturer(@Valid Manufacturer manufacturer, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("product", new Product());
            model.addAttribute("all", this.manufacturerService.findAll());
            return "newManufacturer";
        }

        if(manufacturer.getLocation().equals("")){
            return "redirect:/manufacturer/new?message=Location cannot be empty";
        }
        if(manufacturer.getName().equals("")){
            return "redirect:/manufacturer/new?message=Name cannot be empty";
        }

        this.manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/shop?message=Manufacturer Saved";
    }

    @GetMapping("/delete/{id}")
    public String deleteManufacturer(Model model, @PathVariable Long id){
        try{
            this.manufacturerService.deleteById(id);
            return "redirect:/shop?message=Manufacturer deleted";
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getMessage();
        }

    }

    @GetMapping("/edit/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    //@Secured("ROLE_MODERATOR")
    public String editManufacturer(@PathVariable Long id, Model model){

        try {
            Manufacturer manufacturer = this.manufacturerService.findById(id);
            model.addAttribute("manufacturer", manufacturer);
            model.addAttribute("all", this.manufacturerService.findAll());
            model.addAttribute("msg", "Edit Manufacturer");
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getMessage();
        }


        return "newManufacturer";
    }
}
