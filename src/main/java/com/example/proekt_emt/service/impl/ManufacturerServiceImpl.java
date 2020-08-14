package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.ManufacturerNotFoundException;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.persistance.ManufacturerRepository;
import com.example.proekt_emt.service.ManufacturerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository){
        this.manufacturerRepository = manufacturerRepository;
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
        List<Product> nova = new ArrayList<Product>();
        int i = 0;
        for (Product p: products) {
            if(i < 4 && !p.getId().equals(product.getId()))
            {
                nova.add(p);
                i++;
            }
        }
        return nova;
    }
}
