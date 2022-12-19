package com.kenzie.appserver.service;

import com.kenzie.appserver.config.ReviewCache;
import com.kenzie.appserver.exceptions.ReviewNotFoundException;
import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.repositories.model.ReviewEntity;
import com.kenzie.appserver.repositories.model.ReviewPrimaryKey;
import com.kenzie.appserver.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewCache cache;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewCache cache){
        this.reviewRepository = reviewRepository;
        this.cache = cache;
    }

    public List<Review> getAllByTeacherName(String teacherName) {
        List<Review> cacheReviews = cache.getAllReviewsByTeacherName(teacherName);
        if (cacheReviews != null){
            return cacheReviews;
        }

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

    public List<Review> getAllByUsername(String username) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAllByUsername(username);
        List<Review> reviews = new ArrayList<>();
        reviewEntities.forEach(entity -> reviews.add(convertToReview(entity)));
        return reviews;
    }

    public Review createReview(Review review) {
        review.calculateAndSetTotalRating();
        review.setDatePosted();
        ReviewEntity entity = convertToEntity(review);
        reviewRepository.save(entity);
        cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
        cache.evictAllReviewsByTeacherName(entity.getTeacherName());
        return review;
    }


    public Review updateReview(Review review) {
        Optional<ReviewEntity> result = reviewRepository.findById(new ReviewPrimaryKey(review.getTeacherName(), review.getDatePosted()));
        if (!result.isPresent()) {
            throw new ReviewNotFoundException();
        } else {
            review.calculateAndSetTotalRating();
            ReviewEntity entity = convertToEntity(review);
            reviewRepository.save(entity);
            cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
            cache.evictAllReviewsByTeacherName(entity.getTeacherName());
        }

        return review;
    }

    public void deleteReview(Review review) {
        Optional<ReviewEntity> result = reviewRepository.findById(new ReviewPrimaryKey(review.getTeacherName(), review.getDatePosted()));
        if (!result.isPresent()) {
            throw new ReviewNotFoundException();
        } else {
            ReviewEntity entity = convertToEntity(review);
            reviewRepository.delete(entity);
            cache.evictAllReviewsByCourseTitle(entity.getCourseTitle());
            cache.evictAllReviewsByTeacherName(entity.getTeacherName());
        }
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
