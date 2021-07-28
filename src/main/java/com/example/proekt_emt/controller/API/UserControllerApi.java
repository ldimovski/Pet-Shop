package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Enumerations.MyUserType;
import com.example.proekt_emt.model.Frontend.ManufacturerDTO;
import com.example.proekt_emt.model.Frontend.UserDTO;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.AuthService;
import com.example.proekt_emt.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllerApi {

    private final UserService userService;
    private final AuthService authService;

    public UserControllerApi(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        List<User> users = this.userService.findAll();
        List<UserDTO> finalUsers = new ArrayList<>();
        for (User user :
                users) {
            finalUsers.add(new UserDTO(user.getUsername(),
                    user.getCountry(),
                    user.getCity(),
                    user.getAddress(),
                    user.getUserType(),
                    user.getRoles(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getTermsAndConditions()));
        }
        return finalUsers;
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        if(!user.getPassword().equals("") && !user.getUsername().equals("")){
//            this.userService.registerUser(user);
            this.authService.signUpUser(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getCountry(),
                    user.getCity(),
                    user.getAddress(),
                    MyUserType.INTERNAL,
                    user.getFirstName(),
                    user.getLastName(),
                    user.getTermsAndConditions());
        }
    }

    @PostMapping("/editAddress")
    public void editUserAddress(@RequestBody User user){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User u = this.userService.findById(userDetails.getUsername());
        u.setCountry(user.getCountry());
        u.setCity(user.getCity());
        u.setAddress(user.getAddress());

        this.userService.saveUser(u);
    }

    @GetMapping("/add-moderator/{username}")
    public void addModerator (@PathVariable String username){
        this.userService.addRoleModerator(username);
    }

    @GetMapping("/remove-moderator/{username}")
    public void removeModerator (@PathVariable String username){
        this.userService.removeRoleModerator(username);
    }
}
