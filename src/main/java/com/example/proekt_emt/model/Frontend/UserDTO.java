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

    private String email;

    private MyUserType userType;

    private String firstName;
    private String lastName;

    private Boolean termsAndConditions;

    List<Role> roles;

    public String getUsername() {
        return username;
    }

    public UserDTO(String username,
                   String country,
                   String city,
                   String address,
                   MyUserType userType,
                   List<Role> roles,
                   String email,
                   String firstName,
                   String lastName,
                   Boolean termsAndConditions) {
        this.username = username;
        this.country = country;
        this.city = city;
        this.address = address;
        this.userType = userType;
        this.roles = roles;
        this.email = email;
        this.firstName = firstName;
        this.lastName =lastName;
        this.termsAndConditions = termsAndConditions;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Boolean termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }
}
