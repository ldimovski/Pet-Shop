package com.example.proekt_emt.controller.API;

import com.example.proekt_emt.model.Rating;
import com.example.proekt_emt.service.RatingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingControllerApi {

    private final RatingService ratingService;

    public RatingControllerApi (
            RatingService ratingService
    ){
        this.ratingService = ratingService;
    }

    @GetMapping
    public List<Rating> getAllRatings(){
        return this.ratingService.findAll();
    }

    @GetMapping("/personalRatings")
    public List<Rating> findAllPersonalRatings(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return this.ratingService.findAllRatingsFromUser(userDetails.getUsername());
    }

    @GetMapping("/fromProduct/{productId}")
    public List<Rating> findAllRatingsForProduct (@PathVariable Long productId){
        return this.ratingService.findAllRatingsForProduct(productId);
    }

    @GetMapping("/checkIfUserRatedProduct/{productId}")
    public Boolean checkIfUserRatedProduct (@PathVariable Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return this.ratingService.checkIfUserRatedProduct(userDetails.getUsername(), productId);
    }

    @GetMapping("/findRatingFromUserToProduct/{productId}")
    public Rating findRatingFromUserToProduct (@PathVariable Long productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return this.ratingService.findRatingFromUserToProduct(userDetails.getUsername(), productId);
    }

    @GetMapping("/add/{productId}/{rating}")
    public Rating addRatingFromUserToProduct(@PathVariable Long productId, @PathVariable Integer rating){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return this.ratingService.addRatingFromUserToProduct(userDetails.getUsername(), productId, rating);
    }

    @GetMapping("/averageForProduct/{productId}")
    public Double findAverageRatingForProduct (@PathVariable Long productId){
        return this.ratingService.findAverageRatingForProduct(productId);
    }

    @GetMapping("/numberOfRatingsForProduct/{productId}")
    public Integer findNumberOfRatingsForProduct (@PathVariable Long productId){
        return this.ratingService.findNumberOfRatingsForProduct(productId);
    }
}
