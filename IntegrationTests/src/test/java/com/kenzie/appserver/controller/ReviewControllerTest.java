package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.service.model.Review;
import jdk.jfr.internal.Utils;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class ReviewControllerTest {
    @Autowired
    private MockMvc mvc;
    private QueryUtility queryUtility;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void setup(){
        this.queryUtility = new QueryUtility(mvc);

    }

    @Test
    public void createReview_reviewDoesNotExist_reviewIsCreated() throws Exception {
            //Create a review
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val());

        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isOk());

    }
    @Test
    public void retrieveReviewsForTeacher_reviewExists_reviewIsRetrieved(){
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val());

    }
    @Test
    public void retrieveReviewsForCourse_reviewsExist_reviewsAreRetrieved(){

    }
    @Test
    public void updateReview_reviewExists_reviewIsUpdated() throws Exception {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val());

                queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                        .andExpect(status().isOk());

                //TODO finish writing update portion of this test
    }
    @Test
    public void updateReview_reviewDoesNotExist_reviewIsNotUpdated(){

    }
    @Test
    public void deleteReview_reviewExists_reviewDeleted() throws Exception {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val());
        //queryUtility methods are now available so we don't have to write so much code for the URL's
        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isNoContent());



    }
    @Test
    public void deleteReview_reviewDoesNotExist_reviewDoesNotDelete(){

    }

}
