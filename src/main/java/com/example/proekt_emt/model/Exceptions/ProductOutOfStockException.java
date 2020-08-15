package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProductOutOfStockException extends RuntimeException{

    public ProductOutOfStockException(long id){
        super(String.format("Ths book with the id %d is out of stock!", id));
    }
}
