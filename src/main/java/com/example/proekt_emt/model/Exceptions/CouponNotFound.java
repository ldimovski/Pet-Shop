package com.example.proekt_emt.model.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CouponNotFound extends RuntimeException {
    public CouponNotFound(String code) {
        super(String.format("Coupon with the code: %s is not found!", code));
    }
}
