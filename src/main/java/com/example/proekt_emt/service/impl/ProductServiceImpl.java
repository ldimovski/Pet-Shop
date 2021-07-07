package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.ProductNotFoundException;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.Wishlist;
import com.example.proekt_emt.persistance.ItemRepository;
import com.example.proekt_emt.persistance.ProductRepository;
import com.example.proekt_emt.persistance.WishlistRepository;
import com.example.proekt_emt.service.ItemService;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final WishlistRepository wishlistRepository;


    public ProductServiceImpl(ProductRepository productRepository,
                              ItemRepository itemRepository,
                              WishlistRepository wishlistRepository
                              ) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    @Transactional
    public Product findById(Long id) {
        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product saveProduct(Product product, MultipartFile image) throws IOException {
        if(product.getImageBase64() != null)
        {
            if (image != null && !image.getName().isEmpty()) {
                byte[] bytes = image.getBytes();
                String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
                product.setImageBase64(base64Image);
            }
        }

        return this.productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {


        List<Item> items = this.itemRepository.findAll();

        items.stream()
                .filter(i -> i.getProduct().getId().equals(id))
                .forEach(i -> {
                    this.itemRepository.deleteById(i.getId());
                });

            this.wishlistRepository
                    .findAll()
                    .stream()
                    .forEach(w -> {
                        List<Product> nova = new ArrayList<>();
                        nova = w.getProducts().stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
                        w.setProducts(nova);
                        this.wishlistRepository.save(w);
                    });

        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> getBestProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getNumberSoldItems() {
        return this.productRepository.findAll().stream().mapToInt(p -> p.getSoldItems()).sum();
    }
}
