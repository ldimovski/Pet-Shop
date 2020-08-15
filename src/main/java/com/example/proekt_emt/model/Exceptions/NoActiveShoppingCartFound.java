package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoActiveShoppingCartFound extends RuntimeException{
    public NoActiveShoppingCartFound (String id) {
        super(String.format("No Active shopping cart found for user %s", id));
    }
}
