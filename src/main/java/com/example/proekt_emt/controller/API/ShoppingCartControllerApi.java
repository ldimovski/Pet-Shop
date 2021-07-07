package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Frontend.ShoppingCartDTO;
import com.example.proekt_emt.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartControllerApi {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartControllerApi(ShoppingCartService shoppingCartService){
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public List<ShoppingCartDTO> getAllShoppingCarts(){
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        this.shoppingCartService
                .findALl()
                .stream()
                .forEach(sc -> {
                    shoppingCartDTOS.add(new ShoppingCartDTO(sc));
                });
        return shoppingCartDTOS;
    }

    @PostMapping("/add/{userId}/{productId}")
    public boolean addProductToShoppingCart(@PathVariable String userId, @PathVariable long productId){
        // treba userot da go zima ili preku headerot na povikot ili preku backend avtomatski, ne treba vaka preku URL

        if (this.shoppingCartService.addProductToShoppingCart(userId, productId).getId() > 0) { // ???????
            return true;
        }
        return false;
    }
}
