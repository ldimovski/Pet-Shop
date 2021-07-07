package com.example.proekt_emt.model.Frontend;

import com.example.proekt_emt.model.Enumerations.MyUserType;
import com.example.proekt_emt.model.Role;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

public class UserDTO {

    private String username;

    private String country;
    private String city;
    private String address;

    private MyUserType userType;
    List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MyUserType getUserType() {
        return userType;
    }

    public void setUserType(MyUserType userType) {
        this.userType = userType;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserDTO(String username) {
        this.username = username;
    }
}
