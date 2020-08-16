package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.ProductNotFoundException;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.persistance.ItemRepository;
import com.example.proekt_emt.persistance.ProductRepository;
import com.example.proekt_emt.service.ItemService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ItemRepository itemRepository) {
        this.productRepository = productRepository;
        //this.itemService = itemService;
        this.itemRepository = itemRepository;
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
        if(product.getImageBase64() == null)
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
        for(int i=0; i< items.size(); i++){
            if(items.get(i).getProduct().getId().equals(id)){
                this.itemRepository.deleteById(items.get(i).getId());
            }
        }

        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> getBestProducts() {
        List<Product> products = this.productRepository.findAll();
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Float.compare(o1.getRating(), o2.getRating());
            }
        });
        List<Product> best = new ArrayList<Product>();
        int i = 0;
        Collections.reverse(products);
        for (Product p :
                products) {
            if (i < 4) {
                best.add(p);
                i++;
            }
        }

        return best;
    }
}
