package com.kenzie.appserver.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTableMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.service.model.Review;
import jdk.jfr.internal.Utils;
import net.andreinc.mockneat.MockNeat;
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

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createReview_reviewDoesNotExist_reviewIsCreated() throws Exception {
            //Create a review
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val());

        mvc.perform(post("/api/v1/reviewMyTeacher/teacher").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(reviewCreateRequest)))
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

        mvc.perform(post("/api/v1/reviewMyTeacher/teacher").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(reviewCreateRequest)))
                .andExpect(status().isOk());

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

        mvc.perform(post("/api/v1/reviewMyTeacher/teacher").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(reviewCreateRequest))
                .andExpect(status().isNoContent());



    }
    @Test
    public void deleteReview_reviewDoesNotExist_reviewDoesNotDelete(){

    }

}
