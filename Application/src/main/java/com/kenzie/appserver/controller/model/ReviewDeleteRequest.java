package com.kenzie.appserver.controller.model;

public class ReviewDeleteRequest {
    private String teacherName;
    private String datePosted;

    public ReviewDeleteRequest() {}

    public ReviewDeleteRequest(String teacherName, String datePosted) {
        this.teacherName = teacherName;
        this.datePosted = datePosted;
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
}
