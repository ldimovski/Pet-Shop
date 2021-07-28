package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Enumerations.MyUserType;
import com.example.proekt_emt.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username,
                    String password,
                    String email,
                    String country,
                    String city,
                    String address,
                    MyUserType type,
                    String firstName,
                    String lastName,
                    Boolean termsAndConditions);
}
