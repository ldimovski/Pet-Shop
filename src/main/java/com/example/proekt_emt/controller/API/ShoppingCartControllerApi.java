package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Coupon;
import com.example.proekt_emt.model.Enumerations.CartStatus;
import com.example.proekt_emt.model.Frontend.ShoppingCartDTO;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.model.dto.ChargeRequest;
import com.example.proekt_emt.service.CouponService;
import com.example.proekt_emt.service.ItemService;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartControllerApi {

    private final ShoppingCartService shoppingCartService;
    private final ItemService itemService;
    private final CouponService couponService;

    public ShoppingCartControllerApi(ShoppingCartService shoppingCartService,
                                     ItemService itemService,
                                     CouponService couponService){
        this.shoppingCartService = shoppingCartService;
        this.itemService = itemService;
        this.couponService = couponService;
    }

    @GetMapping
    public List<ShoppingCartDTO> getAllShoppingCarts(){
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        this.shoppingCartService
                .findALl()
                .stream()
                .forEach(sc -> {
                    shoppingCartDTOS.add(new ShoppingCartDTO(sc, itemService.findAllByShoppingCart(sc.getId())));
                });
        return shoppingCartDTOS;
    }

    @GetMapping ("/payed")
    public List<ShoppingCartDTO> getAllPayedShoppingCarts(){
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        this.shoppingCartService
                .findALl()
                .stream()
                .filter(c -> !c.getStatus().equals(CartStatus.CREATED))
                .forEach(sc -> {
                    shoppingCartDTOS.add(new ShoppingCartDTO(sc, itemService.findAllByShoppingCart(sc.getId())));
                });
        return shoppingCartDTOS;
    }

    @GetMapping("/active")
    public ShoppingCartDTO getActiveShoppingCart(){

//        this.shoppingCartService.findALl().forEach(s -> {
//            s.setPrice(this.shoppingCartService.getFullPrice(s.getId()));
//            this.shoppingCartService.save(s);
//        });

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        ShoppingCart sc = this.shoppingCartService.getActiveShoppingCartOrCreateOne(userDetails.getUsername());
        ShoppingCartDTO scDTO = new ShoppingCartDTO(sc,this.itemService.findAllByShoppingCart(sc.getId()));

        return scDTO;
    }

    @GetMapping("/personal")
    public List<ShoppingCartDTO> getPayedShoppingCartsForUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<ShoppingCart> carts = this.shoppingCartService.getFinishedShoppingCart(userDetails.getUsername());
        List<ShoppingCartDTO> cartsDTOs = new ArrayList<>();
        carts.stream().forEach(c -> cartsDTOs.add(new ShoppingCartDTO(c, this.itemService.findAllByShoppingCart(c.getId()))));
        return cartsDTOs;

    }

    @GetMapping("/add/{productId}/{quantity}")
    public boolean addProductToShoppingCart(@PathVariable Long productId, @PathVariable Integer quantity){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        this.shoppingCartService.addProductToShoppingCartQuantity(userDetails.getUsername(), productId, quantity);

        return true;
    }

    @GetMapping("/remove/{productId}")
    public boolean removeProductToShoppingCart(@PathVariable Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        this.shoppingCartService.removeProductFromShoppingCart(userDetails.getUsername(), productId);

        return true;
    }

    @GetMapping("/addDiscount/{couponId}")
    public void addDiscountToCart(@PathVariable String couponId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Coupon c = this.couponService.findById(couponId);
        ShoppingCart sc = this.shoppingCartService.getActiveShoppingCartOrCreateOne(userDetails.getUsername());
        sc.setDiscount(c.getDiscount());
        sc.setPrice(this.shoppingCartService.getFullPrice(sc.getId()));
        this.shoppingCartService.save(sc);
    }

    @GetMapping("/changeStatus/{shoppingCardId}/{status}")
    public void changeShoppingCartStatus(@PathVariable Long shoppingCardId, @PathVariable String status){
        if(status.equals("DELIVERING")){
            this.shoppingCartService.changeStatus(shoppingCardId, CartStatus.DELIVERING);
        }
        else if (status.equals("FINISHED")){
            this.shoppingCartService.changeStatus(shoppingCardId, CartStatus.FINISHED);
        }
        else if(status.equals("PAYED")){
            this.shoppingCartService.changeStatus(shoppingCardId, CartStatus.PAYED);
        }
        else {
            this.shoppingCartService.changeStatus(shoppingCardId, CartStatus.CANCELED);
        }
    }

    @GetMapping("/coupons")
    public List<Coupon> getCoupons(){
        return this.couponService.findAll();
    }

    @PostMapping("/addCoupon")
    public void addCoupon(@RequestBody Coupon coupon){
        this.couponService.addCoupon(coupon);
    }

    @DeleteMapping("/deleteCoupon/{code}")
    public void deleteCoupon (@PathVariable String code){
        this.couponService.deleteById(code);
    }

    @PostMapping("/checkout")
    public void checkoutShoppingCart(@RequestBody ChargeRequest chargeRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        this.shoppingCartService.checkoutShoppingCartStripe(userDetails.getUsername(), chargeRequest);
    }
}
