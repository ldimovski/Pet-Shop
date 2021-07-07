package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Frontend.ProductDTO;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.StoreLocation;
import com.example.proekt_emt.service.StoreLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/store-locations")
public class StoreLocationsControllerApi {

    private final StoreLocationService storeLocationService;

    public StoreLocationsControllerApi(
            StoreLocationService storeLocationService
    ){
        this.storeLocationService = storeLocationService;
    }

    @GetMapping
    public List<StoreLocation> getAllStoreLocations(){
        return this.storeLocationService.findAll();
    }

    @GetMapping("/{id}")
    public StoreLocation getStoreLocationById(@PathVariable long id){
        return this.storeLocationService.findById(id);
    }

    @GetMapping("/products/{id}")
    public List<ProductDTO> getStoreLocationProducts(@PathVariable long id){
        List<ProductDTO> productDTOS = new ArrayList<>();
        this.storeLocationService.getStoreProducts(id)
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteStoreLocationById(@PathVariable long id){
        this.storeLocationService.deleteById(id);
        return true;
    }

    @PostMapping("/add")
    public void addManufacturer(@RequestBody StoreLocation storeLocation){
        if(!storeLocation.getLocation().equals("") && !storeLocation.getName().equals("")){
            this.storeLocationService.save(storeLocation);
        }
    }

    // PostMapping saveProduct
}
