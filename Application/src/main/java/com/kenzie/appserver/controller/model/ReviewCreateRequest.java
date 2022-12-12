package com.kenzie.appserver.controller.model;

public class ReviewCreateRequest {
    private String teacherName;
    private String courseTitle;
    private String comment;
    private double presentation;
    private double outgoing;
    private double subjectKnowledge;
    private double listening;
    private double communication;
    private double availability;

    public ReviewCreateRequest(String teacherName, String courseTitle, String comment, double presentation, double outgoing, double subjectKnowledge, double listening, double communication, double avaibility) {
        this.teacherName = teacherName;
        this.courseTitle = courseTitle;
        this.comment = comment;
        this.presentation = presentation;
        this.outgoing = outgoing;
        this.subjectKnowledge = subjectKnowledge;
        this.listening = listening;
        this.communication = communication;
        this.availability = avaibility;
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
}