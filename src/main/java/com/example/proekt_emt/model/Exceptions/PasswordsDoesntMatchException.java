package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordsDoesntMatchException extends RuntimeException {
    public PasswordsDoesntMatchException() {
        super("Passwords doesn't match!!!");
    }
}
