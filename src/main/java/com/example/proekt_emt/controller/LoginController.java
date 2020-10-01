package com.example.proekt_emt.controller;

import com.example.proekt_emt.config.CustomUsernamePasswordAuthenticationProvider;
import com.example.proekt_emt.model.Enumerations.MyUserType;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.persistance.UserRepository;
import com.example.proekt_emt.service.AuthService;
import com.example.proekt_emt.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController     {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final UserService userService;
    private final CustomUsernamePasswordAuthenticationProvider authManager;

    public LoginController(UserRepository userRepository,
                           AuthService authService,
                           UserService userService,
                           CustomUsernamePasswordAuthenticationProvider authManager){
        this.userRepository = userRepository;
        this.authService = authService;
        this.userService = userService;
        this.authManager = authManager;
    }


    @GetMapping
    public String getLoginPage(@RequestParam(required = false) String info, Model model){
        model.addAttribute("info", info);
        return "login";
    }


    @PostMapping("/signInGoogle/{username}/{pass}")
    public String SignInWithGoogleNew(Model model, @PathVariable String username, @PathVariable String pass){


        if(this.userRepository.existsById(username)){
            //login

            //CustomUsernamePasswordAuthenticationProvider authManager = new CustomUsernamePasswordAuthenticationProvider();
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, pass);
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            return "redirect:/shop";

            //HttpSession session = req.getSession(true);
            //session.setAttribute(httpSess, sc);

        }
        else
        {
            // signup, then login

            this.authService.signUpUser(username, pass, pass, "", "", "", MyUserType.GOOGLE);


            //CustomUsernamePasswordAuthenticationProvider authManager = new CustomUsernamePasswordAuthenticationProvider();
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, pass);
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/shop";

        }
    }

    @PostMapping("/signInGoogle")
    public String SignInWithGoogle(@RequestParam String username, @RequestParam String token){
        if(this.userRepository.existsById(username)){
            //login

            //CustomUsernamePasswordAuthenticationProvider authManager = new CustomUsernamePasswordAuthenticationProvider();
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, token);
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/shop";

            //HttpSession session = req.getSession(true);
            //session.setAttribute(httpSess, sc);

        }
        else
        {
            // signup, then login

            this.authService.signUpUser(username, token, token, "", "", "", MyUserType.GOOGLE);
            //CustomUsernamePasswordAuthenticationProvider authManager = new CustomUsernamePasswordAuthenticationProvider();
            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, token);
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/shop";

        }
    }


}
