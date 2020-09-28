package com.example.proekt_emt.controller;

import com.example.proekt_emt.model.Coupon;
import com.example.proekt_emt.model.Role;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.persistance.RoleRepository;
import com.example.proekt_emt.service.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final StoreLocationService storeLocationService;
    private final AuthService authService;
    private final UserService userService;
    private final CouponService couponService;

    private final RoleRepository roleRepository;

    public AdminController(ProductService productService,
                           ManufacturerService manufacturerService,
                           StoreLocationService storeLocationService,
                           AuthService authService,
                           UserService userService,
                           CouponService couponService,
                           RoleRepository roleRepository){
        this.manufacturerService = manufacturerService;
        this.productService = productService;
        this.storeLocationService = storeLocationService;
        this.authService = authService;
        this.userService = userService;
        this.couponService = couponService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public String adminPage(Model model){

        User user = this.authService.getCurrentUser();
        model.addAttribute("user", user);

        List<Coupon> allCoupons = this.couponService.findAll();
        model.addAttribute("coupons", allCoupons);

        List<User> allUsers = this.userService.findAll();
        model.addAttribute("users", allUsers);

        List<User> moderators = new ArrayList<>();
        List<User> basics = new ArrayList<>();

        for (User u : allUsers){
            int temp = 0;
            for (Role r : u.getRoles()){
                if (r.getName().equals("ROLE_MODERATOR")){
                    moderators.add(u);
                    temp = 1;
                    break;
                }
                if (r.getName().equals("ROLE_ADMIN")){
                    temp = 1;
                }
            }
            if(temp == 0){
                basics.add(u);
            }
        }
        model.addAttribute("moderators", moderators);
        model.addAttribute("basics", basics);
        return "admin";
    }

    @GetMapping("/deleteCoupon/{code}")
    @Secured({"ROLE_ADMIN"})
    public String deleteCoupon(@PathVariable String code, Model model){
        //return "redirect:/shop";
        this.couponService.deleteById(code);
        return "redirect:/admin?message=Coupon Deleted";
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    @GetMapping("/addCoupon/{code}/{discount}")
    @Secured({"ROLE_ADMIN"})
    public String deleteCoupon(@PathVariable String code, @PathVariable String discount){
            Coupon c = new Coupon();
            Integer disc;
            if(isNumeric(discount)){
                disc = Integer.parseInt(discount);
                if(disc < 0 || disc > 99){
                    return "redirect:/admin?message=Add a valid number";
                }
            }
            else {
                return "redirect:/admin?message=Add a valid number";
            }


            c.setCode(code);
            c.setDiscount(disc);
            this.couponService.addCoupon(c);
        return "redirect:/admin?message=Coupon Added";
    }

    @PostMapping("/removeModerator/{username}")
    @Secured({"ROLE_ADMIN"})
    public String removeModerator(@PathVariable String username){
        try {
            User u = this.userService.findById(username);
            List<Role> roles = new ArrayList<Role>();
            Role r = this.roleRepository.findByName("ROLE_BASIC");
            roles.add(r);
            u.setRoles(roles);
            this.userService.saveUser(u);
        }
        catch (RuntimeException ex){
            return "redirect/admin?message=" + ex.getLocalizedMessage();
        }

        return "redirect:/admin";
    }

    @PostMapping("/addModerator/{username}")
    @Secured({"ROLE_ADMIN"})
    public String addModerator(@PathVariable String username){
        try {
            User u = this.userService.findById(username);
            List<Role> roles = new ArrayList<Role>();
            Role r = this.roleRepository.findByName("ROLE_MODERATOR");
            roles.add(r);
            u.setRoles(roles);
            this.userService.saveUser(u);
        }
        catch (RuntimeException ex){
            return "redirect/admin?message=" + ex.getLocalizedMessage();
        }

        return "redirect:/admin";
    }
}
