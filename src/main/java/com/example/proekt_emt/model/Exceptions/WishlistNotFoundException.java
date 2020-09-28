package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistNotFoundException extends RuntimeException{
    public WishlistNotFoundException (Long id) {
        super(String.format("Wishlist with id %s is not found!", id));
    }
}
