package com.example.proekt_emt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController     {
    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String info, Model model){
        model.addAttribute("info", info);
        return "login";
    }
}
