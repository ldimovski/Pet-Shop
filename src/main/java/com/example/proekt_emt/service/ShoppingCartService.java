package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Enumerations.CartStatus;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.model.dto.ChargeRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface ShoppingCartService {
    ShoppingCart createShoppingCart(String userId);
    Item addProductToShoppingCart(String userId, Long productId);
    Item addProductToShoppingCartQuantity(String userId, Long productId, Integer quantity);
    List<Item> removeProductFromShoppingCart(String userId, Long bookId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutShoppingCartStripe(String userId, ChargeRequest chargeRequest);
    ShoppingCart findById(Long shoppingCartId);
    List<Item> findShoppingCartItems(Long shoppingCartId);

    ShoppingCart getActiveShoppingCartOrCreateOne(String userId);
    List<ShoppingCart> getFinishedShoppingCart(String userId);
    Float getFullPrice(Long shoppingCartId);
    ShoppingCart save(ShoppingCart cart);

    List<ShoppingCart> findALl();

    void changeStatus(Long scId, CartStatus status);
    Boolean userHasBoughtProduct(String username, Long productId);
}
