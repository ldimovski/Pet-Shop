package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.ManufacturerNotFoundException;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.persistance.ManufacturerRepository;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ProductService productService;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   ProductService productService){
        this.manufacturerRepository = manufacturerRepository;
        this.productService = productService;
    }


    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        // popravi
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id).orElseThrow(() -> new ManufacturerNotFoundException(id));
    }

    @Override
    @Transactional
    public List<Product> getRelatedProducts(Manufacturer manufacturer, Product product) {
        List<Product> products = manufacturer.getProducts();
        return products.stream()
                .filter(p -> !p.getId().equals(product.getId()))
                .limit(4)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Manufacturer manufacturer = this.findById(id);
        List<Product> products = this.productService.findAll();

        products.stream()
                .filter(p -> p.getManufacturer().getId().equals(id))
                .forEach(p -> {
                    this.productService.deleteById(p.getId());
        });

//        for(int i=0; i<products.size(); i++){
//            if(products.get(i).getManufacturer().getId().equals(id)){
//                this.productService.deleteById(products.get(i).getId());
//            }
//        }
        this.manufacturerRepository.deleteById(id);
    }
}
