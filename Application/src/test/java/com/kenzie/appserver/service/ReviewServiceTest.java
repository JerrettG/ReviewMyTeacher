package com.kenzie.appserver.service;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.kenzie.appserver.config.ReviewCache;
import com.kenzie.appserver.exceptions.ReviewNotFoundException;
import com.kenzie.appserver.repositories.ReviewRepository;
import com.kenzie.appserver.repositories.model.ReviewEntity;
import com.kenzie.appserver.repositories.model.ReviewPrimaryKey;
import com.kenzie.appserver.service.model.Review;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    private ReviewService reviewService;
    private ReviewRepository reviewRepository;
    private
    ReviewCache cache;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @BeforeEach
    public void setup() {
        reviewRepository = mock(ReviewRepository.class);
        cache = mock(ReviewCache.class);
        reviewService = new ReviewService(reviewRepository, cache);
    }

    /**
     * ------------------------------------------------------------------------
     * reviewService.getAllByTeacherName
     * ------------------------------------------------------------------------
     **/
    @Test
    public void getAllByTeacherName() {
        //GIVEN
        String teacherName = randomUUID().toString();
        ReviewEntity entity = new ReviewEntity(
                new ReviewPrimaryKey(
                        teacherName,
                        mockNeat.localDates().valStr()
                ),
                mockNeat.doubles().val(),
                mockNeat.strings().toString(),
                mockNeat.strings().toString(),
                mockNeat.doubles().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val()
        );
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(entity);
        when(cache.getAllReviewsByTeacherName(teacherName)).thenReturn(null);
        when(reviewRepository.findAllByTeacherName(teacherName)).thenReturn(reviewEntities);
        //WHEN
        List<Review> reviews = reviewService.getAllByTeacherName(teacherName);
        //THEN
        Assertions.assertEquals(1, reviews.size());
        Assertions.assertEquals(entity.getTeacherName(), reviews.get(0).getTeacherName());
        Assertions.assertEquals(entity.getCourseTitle(), reviews.get(0).getCourseTitle());
        Assertions.assertEquals(entity.getComment(), reviews.get(0).getComment());
        Assertions.assertEquals(entity.getUsername(), reviews.get(0).getUsername());
        Assertions.assertEquals(entity.getTotalRating(), reviews.get(0).getTotalRating());
        Assertions.assertEquals(entity.getDatePosted(), reviews.get(0).getDatePosted());
        Assertions.assertEquals(entity.getPresentation(), reviews.get(0).getPresentation());
        Assertions.assertEquals(entity.getOutgoing(), reviews.get(0).getOutgoing());
        Assertions.assertEquals(entity.getSubjectKnowledge(), reviews.get(0).getSubjectKnowledge());
        Assertions.assertEquals(entity.getListening(), reviews.get(0).getListening());
        Assertions.assertEquals(entity.getCommunication(), reviews.get(0).getCommunication());
        Assertions.assertEquals(entity.getAvailability(), reviews.get(0).getAvailability());

    }
    @Test
    public void getAllByTeacherName_multipleRequestsForSameTeacher_databaseNotCalled() {
        //GIVEN
        String teacherName = randomUUID().toString();
        Review review = new Review(
                teacherName,
                mockNeat.localDates().valStr(),
                mockNeat.strings().val(),
                mockNeat.strings().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val()
        );
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        when(cache.getAllReviewsByTeacherName(teacherName)).thenReturn(reviews);

        //WHEN
        reviewService.getAllByTeacherName(teacherName);

        //THEN
        verifyZeroInteractions(reviewRepository);
    }

    /**
     * ------------------------------------------------------------------------
     * reviewService.getAllByCourseTitle
     * ------------------------------------------------------------------------
     **/
    @Test
    public void getAllByCourseTitle() {
        //GIVEN
        String courseTitle = randomUUID().toString();
        ReviewEntity entity = new ReviewEntity(
                new ReviewPrimaryKey(
                        mockNeat.names().val(),
                        mockNeat.localDates().valStr()),
                mockNeat.doubles().val(),
                mockNeat.strings().toString(),
                mockNeat.strings().toString(),
                mockNeat.doubles().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val());
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(entity);
        when(cache.getAllReviewsByCourseTitle(courseTitle)).thenReturn(null);
        when(reviewRepository.findAllByCourseTitle(courseTitle)).thenReturn(reviewEntities);
        //WHEN
        List<Review> reviews = reviewService.getAllByCourseTitle(courseTitle);
        //THEN
        Assertions.assertEquals(1, reviews.size());
        Assertions.assertEquals(entity.getTeacherName(), reviews.get(0).getTeacherName());
        Assertions.assertEquals(entity.getCourseTitle(), reviews.get(0).getCourseTitle());
        Assertions.assertEquals(entity.getComment(), reviews.get(0).getComment());
        Assertions.assertEquals(entity.getUsername(), reviews.get(0).getUsername());
        Assertions.assertEquals(entity.getTotalRating(), reviews.get(0).getTotalRating());
        Assertions.assertEquals(entity.getDatePosted(), reviews.get(0).getDatePosted());
        Assertions.assertEquals(entity.getPresentation(), reviews.get(0).getPresentation());
        Assertions.assertEquals(entity.getOutgoing(), reviews.get(0).getOutgoing());
        Assertions.assertEquals(entity.getSubjectKnowledge(), reviews.get(0).getSubjectKnowledge());
        Assertions.assertEquals(entity.getListening(), reviews.get(0).getListening());
        Assertions.assertEquals(entity.getCommunication(), reviews.get(0).getCommunication());
        Assertions.assertEquals(entity.getAvailability(), reviews.get(0).getAvailability());
    }
    @Test
    public void getAllByCourseTitle_multipleRequests_databaseNotCalled() {
        //GIVEN
        String teacherName = randomUUID().toString();
        Review review = new Review(
                teacherName,
                mockNeat.localDates().valStr(),
                mockNeat.strings().val(),
                mockNeat.strings().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val()
        );
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        when(cache.getAllReviewsByCourseTitle(review.getCourseTitle())).thenReturn(reviews);

        //WHEN
        reviewService.getAllByCourseTitle(review.getCourseTitle());

        //THEN
        verifyZeroInteractions(reviewRepository);
    }
    /**
     * ------------------------------------------------------------------------
     * reviewService.getAllByUsername
     * ------------------------------------------------------------------------
     **/
    @Test
    public void getAllByUsername() {
        //GIVEN
        String teacherName = randomUUID().toString();
        ReviewEntity entity = new ReviewEntity(
                new ReviewPrimaryKey(
                        teacherName,
                        mockNeat.localDates().valStr()
                ),
                mockNeat.doubles().val(),
                mockNeat.strings().toString(),
                mockNeat.strings().toString(),
                mockNeat.doubles().toString(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val(),
                mockNeat.doubles().val()
        );
        String username = entity.getUsername();
        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(entity);
        when(reviewRepository.findAllByUsername(username)).thenReturn(reviewEntities);
        //WHEN
        List<Review> reviews = reviewService.getAllByUsername(username);
        //THEN
        Assertions.assertEquals(1, reviews.size());
        Assertions.assertEquals(entity.getTeacherName(), reviews.get(0).getTeacherName());
        Assertions.assertEquals(entity.getCourseTitle(), reviews.get(0).getCourseTitle());
        Assertions.assertEquals(entity.getComment(), reviews.get(0).getComment());
        Assertions.assertEquals(entity.getUsername(), reviews.get(0).getUsername());
        Assertions.assertEquals(entity.getTotalRating(), reviews.get(0).getTotalRating());
        Assertions.assertEquals(entity.getDatePosted(), reviews.get(0).getDatePosted());
        Assertions.assertEquals(entity.getPresentation(), reviews.get(0).getPresentation());
        Assertions.assertEquals(entity.getOutgoing(), reviews.get(0).getOutgoing());
        Assertions.assertEquals(entity.getSubjectKnowledge(), reviews.get(0).getSubjectKnowledge());
        Assertions.assertEquals(entity.getListening(), reviews.get(0).getListening());
        Assertions.assertEquals(entity.getCommunication(), reviews.get(0).getCommunication());
        Assertions.assertEquals(entity.getAvailability(), reviews.get(0).getAvailability());
    }
    /**
     * ------------------------------------------------------------------------
     * reviewService.createReview
     * ------------------------------------------------------------------------
     **/
    @Test
    public void createReview() {
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
        verify(reviewRepository).save(any(ReviewEntity.class));
    }

    /**
     * ------------------------------------------------------------------------
     * reviewService.updateReview
     * ------------------------------------------------------------------------
     **/
    @Test
    public void updateReview() {
        //GIVEN
        Review review = new Review();
        review.setTeacherName(mockNeat.names().toString());
        review.setDatePosted(mockNeat.localDates().toString());
        review.setTotalRating(5);
        review.setCourseTitle(mockNeat.strings().toString());
        review.setUsername(mockNeat.strings().toString());
        review.setComment(mockNeat.strings().toString());
        review.setPresentation(mockNeat.doubles().val());
        review.setOutgoing(mockNeat.doubles().val());
        review.setSubjectKnowledge(mockNeat.doubles().val());
        review.setListening(mockNeat.doubles().val());
        review.setCommunication(mockNeat.doubles().val());
        review.setAvailability(mockNeat.doubles().val());

        ReviewEntity entity = new ReviewEntity(
                new ReviewPrimaryKey(review.getTeacherName(), review.getDatePosted()),
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

        when(reviewRepository.findById(any(ReviewPrimaryKey.class))).thenReturn(Optional.of(entity));
        //WHEN
        Review updatedReview = reviewService.updateReview(review);

        //THEN
        double expectedTotalRating = (review.getAvailability() + review.getCommunication() + review.getListening() + review.getOutgoing()
        + review.getPresentation() + review.getSubjectKnowledge()) / 6.0;
        Assertions.assertNotNull(updatedReview);
        Assertions.assertNotNull(updatedReview.getDatePosted());
        Assertions.assertEquals(expectedTotalRating, updatedReview.getTotalRating());

        verify(reviewRepository).save(any(ReviewEntity.class));
    }
    @Test
    public void updateReview_reviewDoesNotExist_throwsReviewNotFoundException(){
        Review review = new Review();
        review.setTeacherName(mockNeat.names().toString());
        review.setDatePosted(mockNeat.localDates().toString());
        review.setTotalRating(mockNeat.doubles().val());
        review.setCourseTitle(mockNeat.strings().toString());
        review.setUsername(mockNeat.strings().toString());
        review.setComment(mockNeat.strings().toString());
        review.setPresentation(mockNeat.doubles().val());
        review.setOutgoing(mockNeat.doubles().val());
        review.setSubjectKnowledge(mockNeat.doubles().val());
        review.setListening(mockNeat.doubles().val());
        review.setCommunication(mockNeat.doubles().val());
        review.setAvailability(mockNeat.doubles().val());

        when(reviewRepository.findById(any(ReviewPrimaryKey.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        Assertions.assertThrows(ReviewNotFoundException.class, () -> reviewService.updateReview(review));
    }

    /**
     * ------------------------------------------------------------------------
     * reviewService.deleteReview
     * ------------------------------------------------------------------------
     **/
    @Test
    public void deleteReview() {
        //GIVEN
        Review review = new Review();
        review.setTeacherName(mockNeat.names().toString());
        review.setDatePosted(mockNeat.localDates().toString());
        review.setTotalRating(mockNeat.doubles().val());
        review.setCourseTitle(mockNeat.strings().toString());
        review.setUsername(mockNeat.strings().toString());
        review.setComment(mockNeat.strings().toString());
        review.setPresentation(mockNeat.doubles().val());
        review.setOutgoing(mockNeat.doubles().val());
        review.setSubjectKnowledge(mockNeat.doubles().val());
        review.setListening(mockNeat.doubles().val());
        review.setCommunication(mockNeat.doubles().val());
        review.setAvailability(mockNeat.doubles().val());

        ReviewEntity entity = new ReviewEntity(
                new ReviewPrimaryKey(review.getTeacherName(), review.getDatePosted()),
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

        when(reviewRepository.findById(any(ReviewPrimaryKey.class))).thenReturn(Optional.of(entity));

        //WHEN
        reviewService.deleteReview(review);
        //THEN
        verify(reviewRepository).delete(any(ReviewEntity.class));
    }

    @Test
    public void deleteReview_reviewDoesNotExist_throwsReviewNotFoundException(){
        Review review = new Review();
        review.setTeacherName(mockNeat.names().toString());
        review.setDatePosted(mockNeat.localDates().toString());
        review.setTotalRating(mockNeat.doubles().val());
        review.setCourseTitle(mockNeat.strings().toString());
        review.setUsername(mockNeat.strings().toString());
        review.setComment(mockNeat.strings().toString());
        review.setPresentation(mockNeat.doubles().val());
        review.setOutgoing(mockNeat.doubles().val());
        review.setSubjectKnowledge(mockNeat.doubles().val());
        review.setListening(mockNeat.doubles().val());
        review.setCommunication(mockNeat.doubles().val());
        review.setAvailability(mockNeat.doubles().val());

        when(reviewRepository.findById(any(ReviewPrimaryKey.class))).thenReturn(Optional.empty());
        //WHEN
        //THEN
        Assertions.assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReview(review));
    }
}