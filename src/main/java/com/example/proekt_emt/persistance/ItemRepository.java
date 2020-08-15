package com.example.proekt_emt.persistance;

import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
        List<Item> findAllByShoppingCart(Long id);
        List<Item> findAllByProduct(Product product);
        void deleteItemByShoppingCart(ShoppingCart shoppingCart);
        Item findItemByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
}
