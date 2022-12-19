package com.kenzie.appserver.service.model;

import java.time.LocalDateTime;

public class Review {
    private String teacherName;
    private String courseTitle;
    private String datePosted;
    private String username;
    private double totalRating;
    private String comment;
    private double presentation;
    private double outgoing;
    private double subjectKnowledge;
    private double listening;
    private double communication;
    private double availability;
    public Review(){

    }


    public Review(String teacherName, String courseTitle, String datePosted, String username, double totalRating, String comment, double presentation, double outgoing, double subjectKnowledge, double listening, double communication, double availability) {
        this.teacherName = teacherName;
        this.courseTitle = courseTitle;
        this.datePosted = datePosted;
        this.username = username;
        this.totalRating = totalRating;
        this.comment = comment;
        this.presentation = presentation;
        this.outgoing = outgoing;
        this.subjectKnowledge = subjectKnowledge;
        this.listening = listening;
        this.communication = communication;
        this.availability = availability;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
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

    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }

    public void calculateAndSetTotalRating(){
        this.totalRating = (this.presentation +
                this.outgoing +
                this.subjectKnowledge +
                this.listening +
                this.communication +
                this.availability) / 6;
    }
    public void  setDatePosted(){
        this.datePosted = LocalDateTime.now().toString();
    }
}
