package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    @JsonProperty("teacherName")
    private String teacherName;
    @JsonProperty("datePosted")
    private String datePosted;
    @JsonProperty("totalRating")
    private double totalRating;
    @JsonProperty("courseTitle")
    private String courseTitle;
    @JsonProperty("username")
    private String username;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("presentation")
    private double presentation;
    @JsonProperty("outgoing")
    private double outgoing;
    @JsonProperty("subjectKnowledge")
    private double subjectKnowledge;
    @JsonProperty("listening")
    private double listening;
    @JsonProperty("communication")
    private double communication;
    @JsonProperty("avaiability")
    private double avaiability;

    public ReviewResponse(String teacherName, String datePosted, double totalRating, String courseTitle, String username, String comment, double presentation, double outgoing, double subjectKnowledge, double listening, double communication, double avaiability) {
        this.teacherName = teacherName;
        this.datePosted = datePosted;
        this.totalRating = totalRating;
        this.courseTitle = courseTitle;
        this.username = username;
        this.comment = comment;
        this.presentation = presentation;
        this.outgoing = outgoing;
        this.subjectKnowledge = subjectKnowledge;
        this.listening = listening;
        this.communication = communication;
        this.avaiability = avaiability;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getPresentation() {
        return presentation;
    }

    public void setPresentation(double presentation) {
        this.presentation = presentation;
    }

    public double getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    public double getSubjectKnowledge() {
        return subjectKnowledge;
    }

    public void setSubjectKnowledge(double subjectKnowledge) {
        this.subjectKnowledge = subjectKnowledge;
    }

    public double getListening() {
        return listening;
    }

    public void setListening(double listening) {
        this.listening = listening;
    }

    public double getCommunication() {
        return communication;
    }

    public void setCommunication(double communication) {
        this.communication = communication;
    }

    public double getAvaiability() {
        return avaiability;
    }

    public void setAvaiability(double avaiability) {
        this.avaiability = avaiability;
    }
}
