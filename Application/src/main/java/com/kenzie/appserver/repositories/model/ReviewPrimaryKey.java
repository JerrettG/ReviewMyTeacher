package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
public class ReviewPrimaryKey {

    private String teacherName;
    private String datePosted;

    public ReviewPrimaryKey() {}
    public ReviewPrimaryKey(String teacherName, String datePosted) {
        this.teacherName = teacherName;
        this.datePosted = datePosted;
    }

    @DynamoDBHashKey(attributeName = "teacherName")
    public String getTeacherName() {return teacherName;}
    public void setTeacherName(String teacherName) {this.teacherName = teacherName;}
    @DynamoDBRangeKey(attributeName = "datePosted")
    @DynamoDBIndexRangeKey(globalSecondaryIndexNames = {ReviewEntity.COURSE_TITLE_INDEX, ReviewEntity.USERNAME_INDEX})
    public String getDatePosted() {return datePosted;}
    public void setDatePosted(String datePosted) {this.datePosted = datePosted;}

}
