package com.example.proekt_emt.service;

import com.example.proekt_emt.model.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> findAll();
    List<Rating> findAllRatingsFromUser (String username);
    List<Rating> findAllRatingsForProduct (Long productId);
    Boolean checkIfUserRatedProduct (String username, Long productId);
    Rating findRatingFromUserToProduct (String username, Long productId);

    Rating addRatingFromUserToProduct (String username, Long productId, Integer rating);
    void deleteRatingById (Long ratingId);
    void deleteRatingFromUserToProduct (String username, Long productId);

    Double findAverageRatingForProduct (Long productId);
    Integer findNumberOfRatingsForProduct (Long productId);
}
