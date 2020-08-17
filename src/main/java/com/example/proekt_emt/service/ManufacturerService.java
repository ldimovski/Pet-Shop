package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    Manufacturer saveManufacturer(Manufacturer manufacturer);
    Manufacturer findById(Long id);
    List<Product> getRelatedProducts(Manufacturer manufacturer, Product product);
    void deleteById(Long id);
}
