package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "Review")
public class ReviewEntity {

    public static final String DATE_POSTED_INDEX = "DatePostedIndex";
    public static final String COURSE_TITLE_INDEX = "CourseTitleIndex";
    public static final String USERNAME_INDEX = "UsernameIndex";

    private String teacherName;
    private String datePosted;
    private double totalRating;
    private String courseTitle;
    private String username;
    private String comment;
    private double presentation;
    private double outgoing;
    private double listening;
    private double communication;
    private double availability;

    @DynamoDBHashKey(attributeName = "teacherName")
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = DATE_POSTED_INDEX, attributeName = "datePosted")
    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return Double.compare(that.totalRating, totalRating) == 0
                && Double.compare(that.presentation, presentation) == 0
                && Double.compare(that.outgoing, outgoing) == 0
                && Double.compare(that.listening, listening) == 0
                && Double.compare(that.communication, communication) == 0
                && Double.compare(that.availability, availability) == 0
                && Objects.equals(teacherName, that.teacherName)
                && Objects.equals(datePosted, that.datePosted)
                && Objects.equals(courseTitle, that.courseTitle)
                && Objects.equals(username, that.username)
                && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherName,
                datePosted,
                totalRating,
                courseTitle,
                username,
                comment,
                presentation,
                outgoing,
                listening,
                communication,
                availability);
    }
}
