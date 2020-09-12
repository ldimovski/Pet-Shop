package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Coupon;
import com.example.proekt_emt.model.Exceptions.CouponNotFound;
import com.example.proekt_emt.persistance.CouponRepository;
import com.example.proekt_emt.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository){
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon findById(String code) {
        return this.couponRepository.findById(code).orElseThrow(() -> new CouponNotFound(code));
    }

    @Override
    public void deleteById(String code) {
        this.couponRepository.deleteById(code);
    }

    @Override
    public List<Coupon> findAll() {
        return this.couponRepository.findAll();
    }

    @Override
    public Coupon addCoupon(Coupon c) {
        return this.couponRepository.save(c);
    }
}
