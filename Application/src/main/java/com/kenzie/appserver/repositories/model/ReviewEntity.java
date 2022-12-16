package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "Review")
public class ReviewEntity {


    public static final String COURSE_TITLE_INDEX = "CourseTitleIndex";
    public static final String USERNAME_INDEX = "UsernameIndex";
    @Id
    private ReviewPrimaryKey primaryKey;
    private double totalRating;
    private String courseTitle;
    private String username;
    private String comment;
    private double presentation;
    private double outgoing;
    private double subjectKnowledge;
    private double listening;
    private double communication;
    private double availability;

    public ReviewEntity(ReviewPrimaryKey primaryKey, double totalRating, String courseTitle, String username, String comment, double presentation, double outgoing, double subjectKnowledge, double listening, double communication, double availability) {
        this.primaryKey = primaryKey;
        this.totalRating = totalRating;
        this.courseTitle = courseTitle;
        this.username = username;
        this.comment = comment;
        this.presentation = presentation;
        this.outgoing = outgoing;
        this.subjectKnowledge = subjectKnowledge;
        this.listening = listening;
        this.communication = communication;
        this.availability = availability;
    }

    @DynamoDBHashKey(attributeName = "teacherName")
    public String getTeacherName() {
        return primaryKey != null ? primaryKey.getTeacherName() : null;
    }

    public void setTeacherName(String teacherName) {
        if (primaryKey == null)
            primaryKey = new ReviewPrimaryKey();
        primaryKey.setTeacherName(teacherName);
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexNames = {COURSE_TITLE_INDEX, USERNAME_INDEX})
    @DynamoDBRangeKey(attributeName = "datePosted")
    public String getDatePosted() {
        return primaryKey != null ? primaryKey.getDatePosted() : null;
    }

    public void setDatePosted(String datePosted) {
        if (primaryKey == null)
            primaryKey = new ReviewPrimaryKey();
        primaryKey.setTeacherName(datePosted);
    }

    @DynamoDBAttribute(attributeName = "totalRating")
    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = COURSE_TITLE_INDEX, attributeName = "courseTitle")
    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = USERNAME_INDEX, attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @DynamoDBAttribute(attributeName = "presentation")
    public double getPresentation() {
        return presentation;
    }

    public void setPresentation(double presentation) {
        this.presentation = presentation;
    }

    @DynamoDBAttribute(attributeName = "outgoing")
    public double getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    @DynamoDBAttribute(attributeName = "subjectKnowledge")
    public double getSubjectKnowledge() {return subjectKnowledge;}
    public void setSubjectKnowledge(double subjectKnowledge) {this.subjectKnowledge = subjectKnowledge;}
    @DynamoDBAttribute(attributeName = "listening")
    public double getListening() {
        return listening;
    }

    public void setListening(double listening) {
        this.listening = listening;
    }

    @DynamoDBAttribute(attributeName = "communication")
    public double getCommunication() {
        return communication;
    }

    public void setCommunication(double communication) {
        this.communication = communication;
    }

    @DynamoDBAttribute(attributeName = "availability")
    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }
}
