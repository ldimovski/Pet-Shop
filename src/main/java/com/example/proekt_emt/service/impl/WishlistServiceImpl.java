package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.WishlistNotFoundException;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.Wishlist;
import com.example.proekt_emt.persistance.WishlistRepository;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.UserService;
import com.example.proekt_emt.service.WishlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final UserService userService;

    public WishlistServiceImpl(WishlistRepository wishlistRepository,
                               ProductService productService,
                               UserService userService){
        this.wishlistRepository = wishlistRepository;
        this.productService = productService;
        this.userService = userService;
    }
    @Override
    public Wishlist findById(Long id) {
        return this.wishlistRepository.findById(id).orElseThrow(() -> new WishlistNotFoundException(id));
    }

    @Override
    @Transactional
    public Wishlist findByUser(String username) {
        Wishlist wishlist = this.wishlistRepository.findByUserUsername(username);
        if(wishlist == null){
            wishlist = new Wishlist();
            wishlist.setUser(this.userService.findById(username));
            this.wishlistRepository.save(wishlist);
        }
        return wishlist;
    }

    @Override
    @Transactional
    public List<Wishlist> findAll() {
        return this.wishlistRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.wishlistRepository.deleteById(id);
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public Wishlist addProductToWishList(String username, Long productId) {
        Product product = this.productService.findById(productId);
        Wishlist wishlist = this.findByUser(username);
        List<Product> products= wishlist.getProducts();

        if(!products.contains(product)){
            products.add(product);
        }
        wishlist.setProducts(products);
        this.save(wishlist);
        return wishlist;

    }

    @Override
    @Transactional
    public void deleteProductFromWishlist(String username, Long productId) {

        Wishlist wishlist = this.findByUser(username);
        wishlist.setProducts(
                wishlist.getProducts()
                .stream()
                .filter(p -> !p.getId().equals(productId))
                .collect(Collectors.toList())
        );
        this.save(wishlist);

//        Product product = this.productService.findById(productId);
//        Wishlist wishlist = this.findByUser(username);
//        List<Product> products= wishlist.getProducts();
//        List<Product> novaLista = new ArrayList<Product>();
//        for(Product p : products){
//            if(!p.getId().equals(productId)){
//                novaLista.add(p);
//            }
//        }
//        wishlist.setProducts(novaLista);
//        this.save(wishlist);
    }

    @Override
    @Transactional
    public List<Product> findAllProductsForUser(String username) {
        Wishlist wishlist = this.findByUser(username);
        return wishlist.getProducts();
    }


}
