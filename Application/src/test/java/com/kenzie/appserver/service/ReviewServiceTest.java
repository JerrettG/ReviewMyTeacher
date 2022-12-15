package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.service.model.Review;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    private ReviewService reviewService;
    private ReviewRepository reviewRepository;
    private final MockNeat mockNeat = MockNeat.threadLocal();

    @BeforeEach
    public void setup() {
        reviewRepository = mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    void addNewReview() {
        //GIVEN
        Review review = new Review();
        review.setTeacherName(mockNeat.names().toString());
        review.setCourseTitle(mockNeat.strings().toString());
        review.setDatePosted(mockNeat.localDates().toString());
        review.setComment(mockNeat.strings().toString());
        review.setCourseTitle(mockNeat.strings().toString());
        review.setAvailability(mockNeat.doubles().val());
        review.setCommunication(mockNeat.doubles().val());
        review.setOutgoing(mockNeat.doubles().val());
        review.setListening(mockNeat.doubles().val());
        review.setPresentation(mockNeat.doubles().val());
        review.setSubjectKnowledge(mockNeat.doubles().val());
        review.setTotalRating(mockNeat.doubles().val());

        //WHEN
        reviewService.createReview(review);

        //THEN
        verify(reviewRepository).findAllByTeacherName(review.getTeacherName());


    }

    @Test
    void updateReview() {


    }

    @Test
    void updateReview_notFound() {


    }

    @Test
    void getReviewForTeacher() {


    }

    @Test
    void getReviewForTeacher_notFound() {


    }

    @Test
    void getReviewForCourse() {


    }

    @Test
    void getReviewForCourse_notFound() {


    }

    @Test
    void deleteReview() {


    }

    @Test
    void deleteReview_notFound() {


    }
}
