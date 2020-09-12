package com.example.proekt_emt.controller;

import com.example.proekt_emt.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final AuthService authService;

    public SignUpController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping
    public String getSignUpPage(){
        return "signup";
    }

    @PostMapping
    public String signUpUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String repeatPassword,
                             @RequestParam String country,
                             @RequestParam String city,
                             @RequestParam String address){
        try {
            this.authService.signUpUser(username, password, repeatPassword, country, city, address);
            return "redirect:/login?info=SuccessfulRegistration!";
        }
        catch (RuntimeException ex){
            return "redirect:/signup?error=" + ex.getLocalizedMessage();
        }
    }
}
