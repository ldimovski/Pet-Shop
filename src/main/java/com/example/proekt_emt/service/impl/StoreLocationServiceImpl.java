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
        List<Product> products = new ArrayList<Product>();
            StoreLocation storeLocation = this.findById(id);

            if(storeLocation != null)
            {
                for (Product p :
                        this.productService.findAll()) {
                    for (StoreLocation sl : p.getStoreLocations()){
                        if (sl.getId().equals(storeLocation.getId()))
                            products.add(p);
                    }
                }
            }
            return products;
    }

    @Override
    public StoreLocation save(StoreLocation storeLocation) {
        return this.storeLocationRepository.save(storeLocation);
    }

    @Override
    public void deleteById(Long id) {
        List<Product> products = this.productService.findAll();
        StoreLocation stt = this.findById(id);
        for(int i=0; i<products.size(); i++){
            List<StoreLocation> storeLocations = products.get(i).getStoreLocations();
            for (StoreLocation store :
                    storeLocations) {
                if(store.getId().equals(id)){
                    storeLocations.remove(store);
                    Product p = this.productService.findById(products.get(i).getId()) ;
                    p.setStoreLocations(storeLocations);
                    this.productService.saveProduct(p);
                    this.storeLocationRepository.deleteById(id);
                    break;
                }
            }
        }
        this.storeLocationRepository.deleteById(id);
    }
}
