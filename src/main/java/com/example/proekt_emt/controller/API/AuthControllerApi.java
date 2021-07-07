package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.config.CustomUsernamePasswordAuthenticationProvider;
import com.example.proekt_emt.model.Frontend.UserDTO;
import com.example.proekt_emt.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerApi {

    private final CustomUsernamePasswordAuthenticationProvider authManager;
    public AuthControllerApi(CustomUsernamePasswordAuthenticationProvider authManager){
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public Object helloWorld(HttpServletRequest req, @RequestBody User user) {  //@RequestParam String username, @RequestParam String password

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        try {
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = req.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT_KEY", sc);

            User temp = (User)auth.getPrincipal();
            UserDTO u = new UserDTO(temp.getUsername());
            u.setAddress(temp.getAddress());
            u.setCity(temp.getCity());
            u.setCountry(temp.getCountry());
            u.setUserType(temp.getUserType());
            u.setRoles(temp.getRoles());
            return u;
        }
        catch (Exception ex){
            UserDTO u = new UserDTO("NULL");
//            u.setUsername("NULL");
            return u;
        }

    }

    @PostMapping("/loginParam")
    public UserDTO helloWorld2(@RequestParam String username, @RequestParam String password) {


//        sc.getAuthentication();
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            User temp = (User)auth.getPrincipal();
            UserDTO u = new UserDTO(temp.getUsername());
            u.setAddress(temp.getAddress());
            u.setCity(temp.getCity());
            u.setCountry(temp.getCountry());
            u.setUserType(temp.getUserType());
            u.setRoles(temp.getRoles());
            return u;
        }
        catch (Exception ex){
            UserDTO u = new UserDTO("NULL");
//            u.setUsername("NULL");
            return u;
        }

    }

    @GetMapping("/logout")
    public void logOut(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
