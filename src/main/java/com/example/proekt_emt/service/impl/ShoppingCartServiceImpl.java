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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ShoppingCart createShoppingCart(String userId) { // 0 usages ??
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
            this.itemService.saveItem(item);
            shoppingCart.setPrice(this.getFullPrice(shoppingCart.getId()));
            this.shoppingCartRepository.save(shoppingCart);
            return item;
        }
        else {
            throw new ProductNotFoundException(productId);
        }
    }

    @Override
    public List<Item> removeProductFromShoppingCart (String userId, Long itemId) {
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
            shoppingCart.setPrice(this.getFullPrice(shoppingCart.getId()));
            this.save(shoppingCart);
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


            shoppingCart.setStatus(CartStatus.PAYED);
//            User user = this.authService.getCurrentUser();
            User user = this.userService.findById(userId);
            shoppingCart.setCountry(user.getCountry());
            shoppingCart.setCity(user.getCity());
            shoppingCart.setAddress(user.getAddress());
            shoppingCart.setEndDate(LocalDateTime.now());
            shoppingCart.setPrice(price);
            return this.shoppingCartRepository.save(shoppingCart);

        }
    }

    @Override
    public ShoppingCart findById(Long shoppingCartId) {
        return this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new NoShoppingCartFoundException(shoppingCartId));
    }

    @Override
    public List<Item> findShoppingCartItems(Long shoppingCartId) {
        return this.itemService
                .findAll()
                .stream()
                .filter(i -> i.getShoppingCart().getId().equals(shoppingCartId))
                .collect(Collectors.toList());
    }

    @Override
    public ShoppingCart getActiveShoppingCartOrCreateOne(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(this.userService.findById(userId));
            this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> getFinishedShoppingCart(String userId){
        return this.shoppingCartRepository
                .findAll()
                .stream()
                .filter(sc -> sc.getUser().getUsername().equals(userId) && !sc.getStatus().equals(CartStatus.CREATED))
                .collect(Collectors.toList());

    }

    @Override
    public Float getFullPrice(Long shoppingCartId) {

        ShoppingCart cart = this.findById(shoppingCartId);
        float priceFinal = new Double(this.findShoppingCartItems(shoppingCartId)
                .stream()
                .mapToDouble(i -> i.getQuantity() * i.getProduct().getPrice())
                .sum()).floatValue();

        if(!cart.getDiscount().equals(0)){
            priceFinal = priceFinal * (100 - cart.getDiscount())/100;
        }
        return priceFinal;

    }

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        return this.shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public List<ShoppingCart> findALl() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public void changeStatus(Long scId, CartStatus status) {
        ShoppingCart sc = this.findById(scId);
        sc.setStatus(status);
        this.save(sc);
    }

    @Override
    public Boolean userHasBoughtProduct(String username, Long productId) {
        List<ShoppingCart> carts = this.getFinishedShoppingCart(username);
        List<Item> allBoughtItems = new ArrayList<>();
        for (ShoppingCart c : carts){
            allBoughtItems.addAll(this.itemService.findAllByShoppingCart(c.getId()));
        }
        return allBoughtItems
                .stream()
                .anyMatch(i -> i.getProduct().getId().equals(productId));
    }
}
