package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.controller.ManufacturerController;
import com.example.proekt_emt.model.Frontend.ManufacturerDTO;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.service.ManufacturerService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerControllerApi {

    private final ManufacturerService manufacturerService;

    public ManufacturerControllerApi(
            ManufacturerService manufacturerService
    ){
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    @Secured({ "ROLE_ADMIN", "ROLE_MODERATOR" })
    public List<ManufacturerDTO> getAllManufacturers(){
        List<ManufacturerDTO> manufacturerDTOS = new ArrayList<>();
        this.manufacturerService
        .findAll()
        .forEach(m -> {
            manufacturerDTOS.add(new ManufacturerDTO(m));
        });
        return manufacturerDTOS;
    }

    @PostMapping("/add")
    public void addManufacturer(@RequestBody Manufacturer manufacturer){
        if(!manufacturer.getLocation().equals("") && !manufacturer.getName().equals("")){
            this.manufacturerService.saveManufacturer(manufacturer);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteManufacturerById(@PathVariable Integer id){
        this.manufacturerService.deleteById((long)id);
    }
}
