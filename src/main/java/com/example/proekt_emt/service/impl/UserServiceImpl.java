package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.UserExistsException;
import com.example.proekt_emt.persistance.UserRepository;
import com.example.proekt_emt.service.UserService;
import com.example.proekt_emt.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public User findById(String username) {
        return this.userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User registerUser(User user) {
        if(this.userRepository.existsById(user.getUsername())){
            throw new UserExistsException(user.getUsername());
        }
        //user.setCity(" ");
        //user.setCountry(" ");
        //user.setAddress(" ");
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadByUsername(String s) {
        return this.userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

    @Override
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }
}
