package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import com.kenzie.appserver.controller.model.ReviewResponse;
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
            review.setPresentation(reviewCreateRequest.getPresentation());
            review.setOutgoing(reviewCreateRequest.getOutgoing());
            review.setSubjectKnowledge(reviewCreateRequest.getSubjectKnowledge());
            review.setListening(reviewCreateRequest.getListening());
            review.setCommunication(reviewCreateRequest.getCommunication());
            review.setAvailability(reviewCreateRequest.getAvailability());
            reviewService.createReview(review);
            return ResponseEntity.accepted().build();

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
            reviewService.updateReview(review);
            return ResponseEntity.accepted().build();
        } catch (ConditionalCheckFailedException e){
            return ResponseEntity.notFound().build();
        }
    }
}
