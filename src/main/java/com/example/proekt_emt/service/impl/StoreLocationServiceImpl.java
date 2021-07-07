package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.StoreLocationNotFoundException;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.persistance.StoreLocationRepository;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.StoreLocationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {

    private final StoreLocationRepository storeLocationRepository;
    private final ProductService productService;

    public StoreLocationServiceImpl(StoreLocationRepository storeLocationRepository,
                                    ProductService productService){
        this.storeLocationRepository = storeLocationRepository;
        this.productService = productService;
    }


    @Override
    public List<StoreLocation> findAll() {
        return this.storeLocationRepository.findAll();
    }

    @Override
    public StoreLocation findById(Long id) {
        return this.storeLocationRepository.findById(id).orElseThrow(() -> new StoreLocationNotFoundException(id));
    }

    @Override
    public List<Product> getStoreProducts(Long id) {

            StoreLocation storeLocation = this.findById(id);

            if(storeLocation != null)
            {
                return this.productService
                        .findAll()
                        .stream()
                        .filter(p -> p.getStoreLocations().contains(storeLocation))
                        .collect(Collectors.toList());
            }
            return null;
    }

    @Override
    public StoreLocation save(StoreLocation storeLocation) {
        return this.storeLocationRepository.save(storeLocation);
    }

    @Override
    public void deleteById(Long id) {

        this.productService
                .findAll()
                .stream()
                .forEach(p -> {
                    if (p.getStoreLocations().contains(this.findById(id))){
                        p.setStoreLocations(p.getStoreLocations()
                        .stream()
                        .filter(s -> !s.getId().equals(id))
                        .collect(Collectors.toList()));
                        this.productService.saveProduct(p);
                    }
                });
        this.storeLocationRepository.deleteById(id);

    }
}
