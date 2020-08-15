package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoShoppingCartFoundException extends RuntimeException{
    public NoShoppingCartFoundException (Long id) {
        super(String.format("No Shopping card found with the id %d", id));
    }
}
