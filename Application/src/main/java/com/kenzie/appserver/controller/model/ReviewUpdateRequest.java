package com.kenzie.appserver.controller.model;

public class ReviewUpdateRequest {
    private String teacherName;
    private String datePosted;
    private String courseTitle;
    private String comment;
    private String username;
    private double presentation;
    private double outgoing;
    private double subjectKnowledge;
    private double listening;
    private double communication;
    private double availability;

    public ReviewUpdateRequest(String teacherName, String datePosted, String courseTitle, String comment, double presentation, double outgoing, double subjectKnowledge, double listening, double communication, double availability) {
        this.teacherName = teacherName;
        this.datePosted = datePosted;
        this.comment = comment;
        this.courseTitle = courseTitle;
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

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
    public String getCourseTitle() {return courseTitle;}
    public void setCourseTitle(String courseTitle) {this.courseTitle = courseTitle;}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
