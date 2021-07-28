package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.UserExistsException;
import com.example.proekt_emt.model.Role;
import com.example.proekt_emt.persistance.RoleRepository;
import com.example.proekt_emt.persistance.UserRepository;
import com.example.proekt_emt.service.UserService;
import com.example.proekt_emt.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository
    ){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public User findById(String username) {
        return this.userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public Boolean existByUsername(String username){
        try{
            this.findById(username);
             return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public Boolean existByEmail(String email){
//        final boolean temp = false;
        List<User> users = this.findAll();
        for (User u: users) {
            if(u.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addRoleModerator(String username) {
        User u = this.findById(username);
        if(u.getRoles().stream().noneMatch(r -> r.getName().equals("ROLE_MODERATOR"))){
            List<Role> roles = u.getRoles();
            roles.add(this.roleRepository.findByName("ROLE_MODERATOR"));
            u.setRoles(roles);
        }
    }

    @Override
    public void removeRoleModerator(String username) {
        User u = this.findById(username);
        if(u.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_MODERATOR"))){
            List<Role> roles = u.getRoles();
            roles.add(this.roleRepository.findByName("ROLE_MODERATOR"));
            u.setRoles(u.getRoles().stream().filter(r -> !r.getName().equals("ROLE_MODERATOR")).collect(Collectors.toList()));
        }
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
