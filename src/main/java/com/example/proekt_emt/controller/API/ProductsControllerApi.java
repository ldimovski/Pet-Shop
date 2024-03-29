package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Frontend.ProductDTO;
import com.example.proekt_emt.model.Manufacturer;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.ManufacturerService;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins="http://localhost:4200")
public class ProductsControllerApi {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final UserService userService;

    public ProductsControllerApi(
            ProductService productService,
            ManufacturerService manufacturerService,
            UserService userService){
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.userService = userService;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        List<ProductDTO> productDTOS = new ArrayList<>();
        this.productService.findAll()
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id){
        ProductDTO productDTO = new ProductDTO(this.productService.findById(id));
        return productDTO;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Integer id){
        this.productService.deleteById((long)id);
    }

    @GetMapping("/best")
    public List<ProductDTO> getBestProducts(){
        List<ProductDTO> productDTOS = new ArrayList<>();
        this.productService.getBestProducts()
                .stream()
                .forEach(p -> {
                    productDTOS.add(new ProductDTO(p));
                });
        return productDTOS;
    }

    @GetMapping("/related/{productId}")
    public List<ProductDTO> getRelatedProducts(@PathVariable Long productId){
//        Manufacturer man = this.manufacturerService.findById(manufacturerId);
        Product prod = this.productService.findById(productId);
        return this.manufacturerService.getRelatedProducts(prod.getManufacturer(), prod).stream().map(p -> new ProductDTO(p)).collect(Collectors.toList());
    }

    // Na vkupno site produkti
    @GetMapping("/items-sold")
    public Integer getNumberOfSoldItems(){
        return this.productService.getNumberSoldItems();
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product){
       if(product.getPrice() > 0 && product.getAvalibleProducts() >= 0 &&
            product.getSoldItems() >= 0){
           this.productService.saveProduct(product);
       }

    }

}
