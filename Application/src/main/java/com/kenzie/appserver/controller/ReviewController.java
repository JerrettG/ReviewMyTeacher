package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import com.kenzie.appserver.controller.model.ReviewResponse;
import com.kenzie.appserver.exceptions.ReviewNotFoundException;
import com.kenzie.appserver.service.ReviewService;
import com.kenzie.appserver.service.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviewMyTeacher")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/teacher/{teacherName}")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable String teacherName, @RequestBody ReviewCreateRequest reviewCreateRequest) {
            Review review = new Review();
            review.setTeacherName(reviewCreateRequest.getTeacherName());
            review.setComment(reviewCreateRequest.getComment());
            review.setCourseTitle(reviewCreateRequest.getCourseTitle());
            review.setPresentation(reviewCreateRequest.getPresentation());
            review.setOutgoing(reviewCreateRequest.getOutgoing());
            review.setSubjectKnowledge(reviewCreateRequest.getSubjectKnowledge());
            review.setListening(reviewCreateRequest.getListening());
            review.setCommunication(reviewCreateRequest.getCommunication());
            review.setAvailability(reviewCreateRequest.getAvailability());
            Review createdReview = reviewService.createReview(review);
            ReviewResponse reviewResponse = new ReviewResponse(
                    createdReview.getTeacherName(),
                    createdReview.getDatePosted(),
                    createdReview.getTotalRating(),
                    createdReview.getCourseTitle(),
                    createdReview.getUsername(),
                    createdReview.getComment(),
                    createdReview.getPresentation(),
                    createdReview.getOutgoing(),
                    createdReview.getSubjectKnowledge(),
                    createdReview.getListening(),
                    createdReview.getCommunication(),
                    createdReview.getAvailability());
            return ResponseEntity.accepted().body(reviewResponse);

    }

    @PutMapping("/teacher/{teacherName}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable String teacherName, @RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        try {
            Review review = new Review();
            review.setTeacherName(reviewUpdateRequest.getTeacherName());
            review.setComment(reviewUpdateRequest.getComment());
            review.setPresentation(reviewUpdateRequest.getPresentation());
            review.setOutgoing(reviewUpdateRequest.getOutgoing());
            review.setSubjectKnowledge(reviewUpdateRequest.getSubjectKnowledge());
            review.setListening(reviewUpdateRequest.getListening());
            review.setCommunication(reviewUpdateRequest.getCommunication());
            review.setAvailability(reviewUpdateRequest.getAvailability());
            Review updatedReview = reviewService.updateReview(review);
            ReviewResponse reviewResponse = new ReviewResponse(
                    updatedReview.getTeacherName(),
                    updatedReview.getDatePosted(),
                    updatedReview.getTotalRating(),
                    updatedReview.getCourseTitle(),
                    updatedReview.getUsername(),
                    updatedReview.getComment(),
                    updatedReview.getPresentation(),
                    updatedReview.getOutgoing(),
                    updatedReview.getSubjectKnowledge(),
                    updatedReview.getListening(),
                    updatedReview.getCommunication(),
                    updatedReview.getAvailability());
            return ResponseEntity.accepted().body(reviewResponse);
        } catch (ReviewNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    private ReviewResponse convertToResponse(Review review) {
        return new ReviewResponse(
                review.getTeacherName(),
                review.getDatePosted(),
                review.getTotalRating(),
                review.getCourseTitle(),
                review.getUsername(),
                review.getComment(),
                review.getPresentation(),
                review.getOutgoing(),
                review.getSubjectKnowledge(),
                review.getListening(),
                review.getCommunication(),
                review.getAvailability());
    }
}
