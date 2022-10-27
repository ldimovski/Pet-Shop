package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Exceptions.RatingNotFoundException;
import com.example.proekt_emt.model.Rating;
import com.example.proekt_emt.model.ShoppingCart;
import com.example.proekt_emt.persistance.RatingRepository;
import com.example.proekt_emt.service.ProductService;
import com.example.proekt_emt.service.RatingService;
import com.example.proekt_emt.service.ShoppingCartService;
import com.example.proekt_emt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public RatingServiceImpl(
            RatingRepository ratingRepository,
            ProductService productService,
            UserService userService,
            ShoppingCartService shoppingCartService
    ){
        this.ratingRepository = ratingRepository;
        this.productService = productService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public List<Rating> findAll() {
        return this.ratingRepository.findAll();
    }

    @Override
    public List<Rating> findAllRatingsFromUser(String username) {
        return this.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rating> findAllRatingsForProduct(Long productId) {
        return this.findAll()
                .stream()
                .filter(r -> r.getProduct().getId().equals(productId))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkIfUserRatedProduct(String username, Long productId) {
        return this.findAll()
                .stream()
                .anyMatch(r -> r.getProduct().getId().equals(productId) && r.getUser().getUsername().equals(username));
    }

    @Override
    public Rating findRatingFromUserToProduct(String username, Long productId) {
        return this.findAll()
                .stream()
                .filter(r -> r.getProduct().getId().equals(productId) && r.getUser().getUsername().equals(username))
                .findFirst()
                .orElseThrow(RatingNotFoundException::new);
    }

    @Override
    @Transactional
    public Rating addRatingFromUserToProduct(String username, Long productId, Integer rating) {

        if(this.shoppingCartService.userHasBoughtProduct(username, productId)){

            if(this.checkIfUserRatedProduct(username, productId)){
                this.deleteRatingFromUserToProduct(username, productId);
            }

            Rating r = new Rating();
            r.setProduct(this.productService.findById(productId));
            r.setUser(this.userService.findById(username));
            r.setRating(rating);
            return this.ratingRepository.save(r);
        }

        else
            throw new RatingNotFoundException();
    }

    @Override
    public void deleteRatingById(Long ratingId) {
        this.ratingRepository.deleteById(ratingId);
    }

    @Override
    public void deleteRatingFromUserToProduct(String username, Long productId) {
        Rating rating = this.findAll()
                .stream()
                .filter(r -> r.getUser().getUsername().equals(username) && r.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(RatingNotFoundException::new);
        this.deleteRatingById(rating.getId());
    }

    @Override
    public Double findAverageRatingForProduct(Long productId) {
        List<Rating> ratings = this.findAllRatingsForProduct(productId);
        OptionalDouble avg = ratings.stream().mapToInt(Rating::getRating).average();
        return avg.isPresent() ? avg.getAsDouble() : 0;
    }

    @Override
    public Integer findNumberOfRatingsForProduct(Long productId) {
        return this.findAllRatingsForProduct(productId).size();
    }
}
