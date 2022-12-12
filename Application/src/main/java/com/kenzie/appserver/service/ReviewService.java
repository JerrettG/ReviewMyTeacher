package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    //private final ReviewRepository reviewRepository;

    public ReviewService(
           // ReviewRepository reviewRepository
    ){
        //this.reviewRepository = reviewRepository;
    }
    //TODO create constructor with review repository parameter

    public void updateReview(Review review){
        review.calculateAndSetTotalRating();
    }
}
