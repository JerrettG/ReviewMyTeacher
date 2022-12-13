package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.service.model.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public Review createReview(Review review) {
        review.calculateAndSetTotalRating();
        review.setDatePosted();
        //TODO convert review into a entity
        //TODO We need to add a call to repository to create a review
        return review;
    }


    public Review updateReview(Review review) {
        review.calculateAndSetTotalRating();
        //TODO convert review into a entity
        //TODO We need to add a call to repository to update a review
        return review;

    }

}
