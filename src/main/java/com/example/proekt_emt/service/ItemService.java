package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.ShoppingCart;

import java.util.List;

public interface ItemService {
    List<Item> findAllByShoppingCart(Long shoppingCartId);
    void deleteAllByShoppingCart(Long shoppingCartId);
    List<Item> findByBookId(Long bookId);
    Item createItem(Long bookId, Long shoppingCartId);
    void deleteItem(Long cartItemId);
    Item saveItem(Item item);
    Item findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
    List<Item> findAll();
}
