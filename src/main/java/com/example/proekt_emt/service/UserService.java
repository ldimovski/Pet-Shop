package com.example.proekt_emt.service;

import com.example.proekt_emt.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findById(String username);
    User registerUser(User user);
    UserDetails loadByUsername(String s);
    User saveUser(User user);
    List<User> findAll();
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);

    void addRoleModerator(String username);
    void removeRoleModerator(String username);
}
