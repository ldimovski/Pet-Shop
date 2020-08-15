package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.PasswordsDoesntMatchException;
import com.example.proekt_emt.model.Role;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.persistance.RoleRepository;
import com.example.proekt_emt.persistance.UserRepository;
import com.example.proekt_emt.service.AuthService;
import com.example.proekt_emt.service.UserService;
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
            return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    @Override
    public String getCurrentUserId() {
        return getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username, String password, String repeatPassword) {
        if(!password.equals(repeatPassword)){
            throw new PasswordsDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("Basic");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }
}
