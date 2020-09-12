package com.example.proekt_emt.service;

import com.example.proekt_emt.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(String username);
    User registerUser(User user);
    UserDetails loadByUsername(String s);
    User saveUser(User user);
    List<User> findAll();
}
