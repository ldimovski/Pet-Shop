package com.example.proekt_emt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DefaultController {
    @GetMapping
    public String defaultPage (Model model){
        return "redirect:index";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }


    @GetMapping("/contact")
    public String getContactPage(){
        return "contact";
    }
}
