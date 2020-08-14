package com.example.proekt_emt.persistance;

import com.example.proekt_emt.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    //@Query( value = "SELECT * FROM manufacturers", nativeQuery = true)
    public List<Manufacturer> findAll();
}
