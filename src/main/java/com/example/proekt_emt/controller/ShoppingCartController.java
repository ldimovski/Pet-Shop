package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.*;
import com.example.proekt_emt.model.dto.ChargeRequest;
import com.example.proekt_emt.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final CouponService couponService;

    public ShoppingCartController(ProductService productService,
                                  ManufacturerService manufacturerServic,
                                  AuthService authService,
                                  StoreLocationService storeLocationService,
                                  ShoppingCartService shoppingCartService,
                                  ItemService itemService,
                                  CouponService couponService){
        this.productService = productService;
        this.manufacturerService = manufacturerServic;
        this.authService = authService;
        this.storeLocationService = storeLocationService;
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
        this.couponService = couponService;
    }

    @GetMapping
    public String getNew(Model model){

        ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCartOrCreateOne(this.authService.getCurrentUserId());
        model.addAttribute("items", this.shoppingCartService.findShoppingCartItems(shoppingCart.getId()));
        model.addAttribute("cart", shoppingCart);

        int a = 5;

        float price = this.shoppingCartService.getFullPrice(shoppingCart.getId());

        model.addAttribute("amount", (int)price);
        String totalPriceUSD = price + "MKD";
        model.addAttribute("totalPrice", totalPriceUSD);
        model.addAttribute("currency", "eur");
        model.addAttribute("stripePublicKey", this.publicKey);

        User user = this.authService.getCurrentUser();
        model.addAttribute("user", user);

        Boolean userHasAddress = true;

        if(user.getAddress().equals(" ") || user.getCity().equals(" ") || user.getCountry().equals(" ") || user.getCity().equals("") || user.getCountry().equals("") || user.getAddress().equals(""))
        {
            userHasAddress = false;
        }

        model.addAttribute("userHasAddress", userHasAddress);

        return "cart";
    }

    @PostMapping("/coupon")
    public String addCoupon(@RequestParam("couponCode") String code){
        try{
            Coupon coupon = this.couponService.findById(code);
            ShoppingCart shoppingCart = this.shoppingCartService.getActiveShoppingCartOrCreateOne(this.authService.getCurrentUserId());
            shoppingCart.setDiscount(coupon.getDiscount());
            this.shoppingCartService.save(shoppingCart);
        }
        catch (RuntimeException ex){
            return "redirect:/cart?message=" + ex.getLocalizedMessage();
        }


        return "redirect:/cart";
    }

    @PostMapping("/add/{id}")
    public String addProductToShoppingCart(@PathVariable Long id){
        try{
            Product p = this.productService.findById(id);
            if(p.getAvalibleProducts() == 0){
                return "redirect:/shop?message=That product is not in stock";
            }
            Item item = this.shoppingCartService.addProductToShoppingCart(this.authService.getCurrentUserId(), id);
        }
        catch (RuntimeException ex){
            return "redirect:/shop?message=" + ex.getLocalizedMessage();
        }
        return "redirect:/shop?message=Added to cart    ";
    }

    @PostMapping("/add/{id}/{quantity}")
    public String addProductToShoppingCartQuantity(@PathVariable Long id, @PathVariable Integer quantity){
        try{
            Product p = this.productService.findById(id);
            if(p.getAvalibleProducts() < quantity){
                return "redirect:/shop?message=That product does not have " + quantity + " instances in stock";
            }
            Item item = this.shoppingCartService.addProductToShoppingCartQuantity(this.authService.getCurrentUserId(), id, quantity);
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
