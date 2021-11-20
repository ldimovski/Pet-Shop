package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Mail;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.UserService;
import com.example.proekt_emt.service.WishlistService;
import com.example.proekt_emt.service.impl.EmailSenderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailControllerApi {

    private final EmailSenderService emailSenderService;
    private final UserService userService;

    public EmailControllerApi(
            EmailSenderService emailSenderService,
            UserService userService
    ){
        this.emailSenderService = emailSenderService;
        this.userService = userService;
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody Mail mail){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        User user = this.userService.findById(userDetails.getUsername());
        this.emailSenderService.sendEmail(mail);

    }

    @PostMapping("/sendEmail/all")
    public void sendEmailAll(@RequestBody Mail mail){
        this.emailSenderService.sendEmailAll(mail);
    }


}
