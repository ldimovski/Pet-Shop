package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Coupon;

import java.util.List;

public interface CouponService {
    Coupon findById(String code);
    void deleteById(String code);
    List<Coupon> findAll();
    Coupon addCoupon(Coupon c);
}
