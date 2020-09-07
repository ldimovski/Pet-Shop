package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.*;
import com.example.proekt_emt.service.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final AuthService authService;
    private final StoreLocationService storeLocationService;
    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;
    private final  UserService userService;

    public UserController(ProductService productService,
                                  ManufacturerService manufacturerServic,
                                  AuthService authService,
                                  StoreLocationService storeLocationService,
                                  ShoppingCartService shoppingCartService,
                                  ItemService itemService,
                          UserService userService){
        this.productService = productService;
        this.manufacturerService = manufacturerServic;
        this.authService = authService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserProfile(Model model){

        User user = this.authService.getCurrentUser();
        model.addAttribute("user", user);

        List<Container> containers = new ArrayList<Container>();

        List<ShoppingCart> carts = this.shoppingCartService.getFinishedShoppingCart(user.getUsername());
        for (ShoppingCart cart :  carts) {
            Container temp = new Container();
            temp.setShoppingCart(cart);
            temp.setPrice(this.shoppingCartService.getFullPrice(cart.getId()) + " MKD");
            temp.setAddress(cart.getCountry() + "/" + cart.getCity() + "/" + cart.getAddress());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            temp.setEndDateString(cart.getEndDate().format(formatter));
            containers.add(temp);

        }

        model.addAttribute("carts", containers);
        return "user";
    }

    @PostMapping
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model){

        if(user.getAddress().equals("")){
            return "redirect:/user/new?message=Address cannot be empty";
        }
        if(user.getCountry().equals("")){
            return "redirect:/user/new?message=Country cannot be empty";
        }
        if(user.getCity().equals("")){
            return "redirect:/user/new?message=City cannot be empty";
        }

        this.userService.saveUser(user);
        return "redirect:/logout";
    }
}
