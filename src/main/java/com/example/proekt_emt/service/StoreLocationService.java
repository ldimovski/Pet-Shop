package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;

import java.util.List;

public interface StoreLocationService {
    List<StoreLocation> findAll();
    StoreLocation findById(Long id);
    List<Product> getStoreProducts(Long id);
    StoreLocation save(StoreLocation storeLocation);
}
