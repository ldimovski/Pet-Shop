package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActiveShoppingCartExistsException extends RuntimeException{
    public ActiveShoppingCartExistsException (String id) {
        super(String.format("A shopping cart for the user %d is already created", id));
    }
}
