package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.model.dto.ChargeRequest;
import com.example.proekt_emt.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final AuthService authService;
    private final StoreLocationService storeLocationService;
    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;

    public ShoppingCartController(ProductService productService,
                                  ManufacturerService manufacturerServic,
                                  AuthService authService,
                                  StoreLocationService storeLocationService,
                                  ShoppingCartService shoppingCartService,
                                  ItemService itemService){
        this.productService = productService;
        this.manufacturerService = manufacturerServic;
        this.authService = authService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
    }

    @GetMapping
    public String getNew(Model model){

        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCartOrCreateOne(this.authService.getCurrentUserId());
        model.addAttribute("items", this.shoppingCartService.findShoppingCartItems(shoppingCart.getId()));
        model.addAttribute("cart", shoppingCart);

        int a = 5;

        float price = this.shoppingCartService.getFullPrice(shoppingCart.getId());

        model.addAttribute("amount", (int)price);
        String totalPriceUSD = price + "$";
        model.addAttribute("totalPrice", totalPriceUSD);
        model.addAttribute("currency", "eur");
        model.addAttribute("stripePublicKey", this.publicKey);

        User user = this.authService.getCurrentUser();
        model.addAttribute("user", user);

        return "cart";
    }

    @PostMapping("/add/{id}")
    public String addProductToShoppingCart(@PathVariable Long id){
        try{
            Item item = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), id);
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getLocalizedMessage();
        }
        return "redirect:/shop";
    }

    @PostMapping("/remove/{id}")
    public String removeProductFromShoppingCart(@PathVariable Long id){
        List<Item> items = this.shoppingCartService.removeBookFromShoppingCart(this.authService.getCurrentUserId(), id);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutShoppingCartStripe(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/shop?message=Successful Payment";
        } catch (RuntimeException ex) {
            return "redirect:/shop?message=" + ex.getLocalizedMessage();
        }
    }

}
