package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StoreLocationNotFoundException extends RuntimeException{
    public StoreLocationNotFoundException (Long id) {
        super(String.format("Store Location with id %d is not found!", id));
    }
}
