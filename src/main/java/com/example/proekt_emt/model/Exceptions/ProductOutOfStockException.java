package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProductOutOfStockException extends RuntimeException{

    public ProductOutOfStockException(String name){
        super(String.format("Ths book with the name %s is out of stock!", name));
    }
}
