package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewDeleteRequest;
import com.kenzie.appserver.controller.model.ReviewResponse;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.users().valStr(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());

        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("teacherName").value(reviewCreateRequest.getTeacherName()),
                        jsonPath("courseTitle").value(reviewCreateRequest.getCourseTitle()),
                        jsonPath("comment").value(reviewCreateRequest.getComment()),
                        jsonPath("availability").value(reviewCreateRequest.getAvailability()),
                        jsonPath("outgoing").value(reviewCreateRequest.getOutgoing()),
                        jsonPath("subjectKnowledge").value(reviewCreateRequest.getSubjectKnowledge()),
                        jsonPath("communication").value(reviewCreateRequest.getCommunication()),
                        jsonPath("presentation").value(reviewCreateRequest.getPresentation()),
                        jsonPath("listening").value(reviewCreateRequest.getListening())
                );

    }

    @Test
    public void retrieveReviewsForTeacher_reviewExists_reviewIsRetrieved() throws Exception{
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.users().valStr(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());

        String json = queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        ReviewResponse reviewResponse = mapper.readValue(json, ReviewResponse.class);
        queryUtility.reviewControllerClient.getAllReviewsForTeacher(reviewCreateRequest.getTeacherName())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherName").value(reviewResponse.getCourseTitle()))
                .andExpect(jsonPath("$[0].datePosted").value(reviewResponse.getDatePosted()));

    }

    @Test
    public void retrieveReviewsForCourse_reviewsExist_reviewsAreRetrieved() throws Exception{
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.users().valStr(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());

        String json = queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        ReviewResponse reviewResponse = mapper.readValue(json, ReviewResponse.class);
        queryUtility.reviewControllerClient.getAllReviewsForCourse(reviewCreateRequest.getCourseTitle())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseTitle").value(reviewResponse.getCourseTitle()))
                .andExpect(jsonPath("$[0].datePosted").value(reviewResponse.getDatePosted()));
    }

    @Test
    public void updateReview_reviewExists_reviewIsUpdated() throws Exception {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.users().valStr(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());

        String json = queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        ReviewResponse reviewResponse = mapper.readValue(json, ReviewResponse.class);
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(
                reviewResponse.getTeacherName(),
                reviewResponse.getDatePosted(),
                mockNeat.strings().val(),
                reviewResponse.getPresentation(),
                reviewResponse.getOutgoing(),
                reviewResponse.getSubjectKnowledge(),
                reviewResponse.getListening(),
                reviewResponse.getCommunication(),
                reviewResponse.getAvaiability());

               queryUtility.reviewControllerClient.updateReview(reviewUpdateRequest)
                       .andExpect(status().isAccepted())
                       .andExpect(jsonPath("teacherName").value(reviewResponse.getTeacherName()))
                       .andExpect(jsonPath("datePosted").value(reviewResponse.getDatePosted()))
                       .andExpect(jsonPath("comment").value(reviewUpdateRequest.getComment()));
    }

    @Test
    public void updateReview_reviewDoesNotExist_reviewIsNotUpdated() throws Exception {
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.strings().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());
        queryUtility.reviewControllerClient.updateReview(reviewUpdateRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteReview_reviewExists_reviewDeleted() throws Exception {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(mockNeat.strings().val(),
                mockNeat.strings().val(), mockNeat.strings().val(), mockNeat.users().valStr(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val(),
                mockNeat.doubles().val(), mockNeat.doubles().val(), mockNeat.doubles().val());

        String json = queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        ReviewResponse reviewResponse = mapper.readValue(json, ReviewResponse.class);
        ReviewDeleteRequest reviewDeleteRequest = new ReviewDeleteRequest(reviewResponse.getTeacherName(), reviewResponse.getDatePosted());
        queryUtility.reviewControllerClient.deleteReview(reviewDeleteRequest)
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteReview_reviewDoesNotExist_reviewDoesNotDelete() throws Exception{
        ReviewDeleteRequest reviewDeleteRequest = new ReviewDeleteRequest(mockNeat.strings().val(),
                mockNeat.strings().val());
        queryUtility.reviewControllerClient.deleteReview(reviewDeleteRequest)
                .andExpect(status().isBadRequest());
    }

}
