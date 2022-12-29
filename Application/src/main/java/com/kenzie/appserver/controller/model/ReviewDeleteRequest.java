package com.kenzie.appserver.controller.model;

public class ReviewDeleteRequest {
    private String teacherName;
    private String datePosted;
    private String courseTitle;

    public ReviewDeleteRequest(String teacherName, String datePosted, String courseTitle) {
        this.teacherName =teacherName;
        this.datePosted = datePosted;
        this.courseTitle = courseTitle;
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

    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
