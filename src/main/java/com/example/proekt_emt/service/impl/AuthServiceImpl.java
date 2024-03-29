package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Enumerations.MyUserType;
import com.example.proekt_emt.model.Exceptions.PasswordsDoesntMatchException;
import com.example.proekt_emt.model.Role;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.persistance.RoleRepository;
import com.example.proekt_emt.persistance.UserRepository;
import com.example.proekt_emt.service.AuthService;
import com.example.proekt_emt.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthServiceImpl(UserService userService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @Override
    public User getCurrentUser() {

        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken){
            return null;
        }

        else {
            return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

    }

    @Override
    public String getCurrentUserId() {
        return getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username,
                           String password,
                           String email,
                           String country,
                           String city,
                           String address,
                           MyUserType type,
                           String firstName,
                           String lastName,
                           Boolean termsAndConditions) {
//        if(!password.equals(repeatPassword)){
//            throw new PasswordsDoesntMatchException();
//        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        user.setCountry(country);
        user.setCity(city);
        user.setAddress(address);
        Role userRole = this.roleRepository.findByName("ROLE_BASIC");
        user.setRoles(Collections.singletonList(userRole));
        user.setUserType(type);
        user.setTermsAndConditions(termsAndConditions);
        return this.userService.registerUser(user);
    }
}
