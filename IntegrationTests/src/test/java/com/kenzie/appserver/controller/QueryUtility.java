package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.ReviewCreateRequest;
import com.kenzie.appserver.controller.model.ReviewDeleteRequest;
import com.kenzie.appserver.controller.model.ReviewUpdateRequest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class QueryUtility {


    public ReviewControllerClient reviewControllerClient;


    private final MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
    }

    public class ReviewControllerClient {
        public ResultActions getAllReviewsForTeacher(String teacherName) throws Exception {
            return mvc.perform(get("/api/v1/reviewMyTeacher/teacher/{teacherName}", teacherName)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions getAllReviewsForCourse(String courseTitle) throws Exception {
            return mvc.perform(get("/api/v1/reviewMyTeacher/course/{courseTitle}", courseTitle)
                    .accept(MediaType.APPLICATION_JSON));
        }

        public ResultActions createReview(ReviewCreateRequest createRequest) throws Exception {
            return mvc.perform(post("/api/v1/reviewMyTeacher/teacher")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(createRequest)));
        }
        public ResultActions updateReview(ReviewUpdateRequest updateRequest) throws Exception {
            return mvc.perform(put("/api/v1/reviewMyTeacher/teacher/{teacherName}", updateRequest.getTeacherName())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updateRequest)));
        }
        public ResultActions deleteReview(ReviewDeleteRequest deleteRequest) throws Exception {
            return mvc.perform(delete("/api/v1/reviewMyTeacher/teacher/{teacherName}", deleteRequest.getTeacherName())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(deleteRequest)));
        }
    }
}

