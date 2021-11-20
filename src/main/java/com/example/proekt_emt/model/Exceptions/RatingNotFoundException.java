package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class RatingNotFoundException extends RuntimeException{
    public RatingNotFoundException(){
        super("Rating does not exist");
    }
}
