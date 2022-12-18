package com.kenzie.appserver.service;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.kenzie.appserver.config.ReviewCache;
import com.kenzie.appserver.exceptions.ReviewNotFoundException;
import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.repositories.model.ReviewEntity;
import com.kenzie.appserver.repositories.model.ReviewPrimaryKey;
import com.kenzie.appserver.service.model.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewCache cache;

    public ReviewService(ReviewRepository reviewRepository, ReviewCache cache) {
        this.reviewRepository = reviewRepository;
        this.cache = cache;
    }

    public List<Review> getAllByTeacherName(String teacherName) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAllByTeacherName(teacherName);
        List<Review> reviews = new ArrayList<>();
        reviewEntities.forEach(entity -> reviews.add(convertToReview(entity)));
        return reviews;
    }

    public List<Review> getAllByCourseTitle(String courseTitle) {
        List<Review> cachedReview = cache.getAllReviewsByCourseTitle(courseTitle);

        if(cachedReview != null) {
            return cachedReview;
        }

        List<ReviewEntity> reviewEntities = reviewRepository.findAllByCourseTitle(courseTitle);
        List<Review> reviews = new ArrayList<>();
        reviewEntities.forEach(entity -> reviews.add(convertToReview(entity)));
        cache.addAllReviewsByCourseTitle(courseTitle, reviews);
        return reviews;
    }

    public Review createReview(Review review) {
        review.calculateAndSetTotalRating();
        review.setDatePosted();
        ReviewEntity entity = convertToEntity(review);
        reviewRepository.save(entity);
        cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
        return review;
    }


    public Review updateReview(Review review) {
        review.calculateAndSetTotalRating();
        ReviewEntity entity = convertToEntity(review);
        try{
            reviewRepository.save(entity);
            cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
        } catch (ConditionalCheckFailedException e){
            throw new ReviewNotFoundException();
        }
        return review;
    }

    public void deleteReview(Review review) {
        /* TODO check if review exists before deleting. throw custom exception if not found.
            Reason: DynamoDB does not throw an exception if item does not exist so no indication if successful
        */

        ReviewEntity entity = convertToEntity(review);
        reviewRepository.delete(entity);
        cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
    }


    private ReviewEntity convertToEntity(Review review) {
        return new ReviewEntity(
                new ReviewPrimaryKey(
                        review.getTeacherName(),
                        review.getDatePosted()
                ),
                review.getTotalRating(),
                review.getCourseTitle(),
                review.getUsername(),
                review.getComment(),
                review.getPresentation(),
                review.getOutgoing(),
                review.getSubjectKnowledge(),
                review.getListening(),
                review.getCommunication(),
                review.getAvailability()
        );
    }

    private Review convertToReview(ReviewEntity entity) {
        return new Review(
                entity.getTeacherName(),
                entity.getCourseTitle(),
                entity.getDatePosted(),
                entity.getUsername(),
                entity.getTotalRating(),
                entity.getComment(),
                entity.getPresentation(),
                entity.getOutgoing(),
                entity.getSubjectKnowledge(),
                entity.getListening(),
                entity.getCommunication(),
                entity.getAvailability()
        );
    }
}
