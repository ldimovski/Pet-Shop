package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product saveProduct(Product product, MultipartFile image) throws IOException;
    Product saveProduct(Product product);
    void deleteById(Long id);
    List<Product> getBestProducts();
    Integer getNumberSoldItems();
}
