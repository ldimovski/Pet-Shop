package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.NoShoppingCartFoundException;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.persistance.ItemRepository;
import com.example.proekt_emt.persistance.ShoppingCartRepository;
import com.example.proekt_emt.service.ItemService;
import com.example.proekt_emt.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductService productService;
    private final ShoppingCartRepository shoppingCartRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ProductService productService,
                           ShoppingCartRepository shoppingCartRepository){
        this.itemRepository = itemRepository;
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<Item> findAllByShoppingCart(Long shoppingCartId) {

        List<Item> finalItem = new ArrayList<>();
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
        if (shoppingCart != null){
            List<Item> items = this.findAll();
            for (Item item :
                    items) {
                if(item.getShoppingCart().getId().equals(shoppingCartId))
                    finalItem.add(item);
            }
        }
        return finalItem;
//        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
//        if (shoppingCart != null){
//            List<Item> items = itemRepository.findAllByShoppingCart(shoppingCartId);
//            return items;
//        }
//        return null;
    }

    @Override
    public void deleteAllByShoppingCart(Long shoppingCartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
        if(shoppingCart != null){
            this.itemRepository.deleteItemByShoppingCart(shoppingCart);
        }
    }

    @Override
    public List<Item> findByBookId(Long productId) {
        Product p = this.productService.findById(productId);
        return this.itemRepository.findAllByProduct(p);
    }

    @Override
    public Item createItem(Long productId, Long shoppingCartId) {
        Item item = new Item();
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
        Product p = this.productService.findById(productId);
        if(shoppingCart != null && p != null){
            item.setProduct(p);
            item.setShoppingCart(shoppingCart);
            //item.setQuantity(1);
            return itemRepository.save(item);
        }
        return null;
    }

    @Override
    public void deleteItem(Long cartItemId) {
        itemRepository.deleteById(cartItemId);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product) {
        return this.itemRepository.findItemByShoppingCartAndProduct(shoppingCart, product);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

}
