package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Coupon;

public interface CouponService {
    Coupon findById(String code);
    void deleteById(String code);
}
