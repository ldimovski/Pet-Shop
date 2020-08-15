package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Enumerations.CartStatus;
import com.example.proekt_emt.model.Exceptions.*;
import com.example.proekt_emt.model.Item;
import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.model.dto.ChargeRequest;
import com.example.proekt_emt.persistance.ShoppingCartRepository;
import com.example.proekt_emt.service.*;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final AuthService authService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserService userService,
                                   ProductService productService,
                                   ItemService itemService,
                                   PaymentService paymentService,
                                   AuthService authService){
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.productService = productService;
        this.itemService = itemService;
        this.paymentService = paymentService;
        this.authService = authService;
    }


    @Override
    public ShoppingCart createShoppingCart(String userId) {
            User user = this.userService.findById(userId);
            if(this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED)){
                throw new ActiveShoppingCartExistsException(userId);
            }
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public Item addProductToShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateOne(userId);
        Product product = this.productService.findById(productId);
        if(product != null){
            Item item = this.itemService.findByShoppingCartAndProduct(shoppingCart, product);
            if(item == null){
                item = new Item();
                item.setQuantity(1);
                item.setShoppingCart(shoppingCart);
                item.setProduct(product);
            }
            else {
                item.setQuantity(item.getQuantity() + 1);
            }
            return this.itemService.saveItem(item);
        }
        else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Override
    public Item addProductToShoppingCartQuantity(String userId, Long productId, Integer quantity) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateOne(userId);
        Product product = this.productService.findById(productId);
        if(product != null){
            Item item = this.itemService.findByShoppingCartAndProduct(shoppingCart, product);
            if(item == null){
                item = new Item();
                item.setQuantity(quantity);
                item.setShoppingCart(shoppingCart);
                item.setProduct(product);
            }
            else {
                item.setQuantity(item.getQuantity() + quantity);
            }
            return this.itemService.saveItem(item);
        }
        else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Override
    public List<Item> removeBookFromShoppingCart(String userId, Long itemId) {
        if(!this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED)){
            throw new NoActiveShoppingCartFound(userId);
        }
        else {
            ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateOne(userId);
            List<Item> items = this.findShoppingCartItems(shoppingCart.getId());
            for(Item item: items){
                if(item.getId().equals(itemId)){
                    this.itemService.deleteItem(item.getId());
                    break;
                }
            }
            return this.findShoppingCartItems(shoppingCart.getId());
        }
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);

        if(shoppingCart == null){
            throw new NoActiveShoppingCartFound(userId);
        }
        else
        {
            shoppingCart.setStatus(CartStatus.CANCELED);
            shoppingCart.setEndDate(LocalDateTime.now());
            return this.shoppingCartRepository.save(shoppingCart);
        }
    }

    @Override
    public ShoppingCart checkoutShoppingCartStripe(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if (shoppingCart == null){
            throw new NoActiveShoppingCartFound(userId);
        }

        else {
            List<Item> items = this.findShoppingCartItems(shoppingCart.getId());
            for (Item item : items){
                if(item.getProduct().getAvalibleProducts() < item.getQuantity()){
                    throw new ProductOutOfStockException(item.getProduct().getName());
                }
                if (! (item.getProduct().getAvalibleProducts() < item.getQuantity())){
                    item.getProduct().setAvalibleProducts(item.getProduct().getAvalibleProducts() - item.getQuantity());
                    item.getProduct().setSoldItems(item.getProduct().getSoldItems() + item.getQuantity());
                }
            }

            float price = this.getFullPrice(shoppingCart.getId());
            Charge charge = null;

            try {
                charge = this.paymentService.pay(chargeRequest);
                //Transaction transaction = new Transaction();
                //transaction.setId(charge.getId());
                //transaction.setBalanceTransaction(charge.getBalanceTransaction());
                //transaction.setUserId(userId);
                //transaction.setStatus(charge.getStatus());
                //this.transactionService.saveTransaction(transaction);
            }
            catch(CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e){
                throw new TransactionFailedException(userId, e.getMessage());
            }


            //shoppingCart.setItems(items);
            shoppingCart.setStatus(CartStatus.FINISHED);
            User user = this.authService.getCurrentUser();
            shoppingCart.setCountry(user.getCountry());
            shoppingCart.setCity(user.getCity());
            shoppingCart.setAddress(user.getAddress());
            shoppingCart.setEndDate(LocalDateTime.now());

            //this.itemService.deleteAllByShoppingCart(shoppingCart.getId());
            return this.shoppingCartRepository.save(shoppingCart);

        }
    }

    @Override
    public ShoppingCart findById(Long shoppingCartId) {
        return this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
    }

    @Override
    public List<Item> findShoppingCartItems(Long shoppingCartId) {
        List<Item> items = this.itemService.findAll();

        List<Item> finalItems = new ArrayList<Item>();

        for(Item item : items){
            if(item.getShoppingCart().getId().equals(shoppingCartId))
            {
                finalItems.add(item);
            }
        }

        return finalItems;
    }

    @Override
    public ShoppingCart getActiveShoppingCartOrCreateOne(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        //      .orElseThrow(() -> new NoActiveShoppingCartFound(userId));
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(this.userService.findById(userId));
            this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public Float getFullPrice(Long shoppingCartId) {
        List<Item> items = this.findShoppingCartItems(shoppingCartId);
        float price = 0;
        for (Item item : items){
            float tmp = item.getQuantity() * item.getProduct().getPrice();
            price = price + tmp;
        }
        return price;
    }
}
