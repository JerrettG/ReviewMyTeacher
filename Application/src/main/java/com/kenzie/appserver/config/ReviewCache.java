package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Review;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReviewCache {
    private final Cache<String, List<Review>> courseTitleCache;
    private final Cache<String, List<Review>> teacherNameCache;

    public ReviewCache(int expiry, TimeUnit timeUnit, long maximumSize) {
        this.courseTitleCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .maximumSize(maximumSize)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
        this.teacherNameCache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .maximumSize(maximumSize)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public List<Review> getAllReviewsByCourseTitle(String courseTitle){
        return courseTitleCache.getIfPresent(courseTitle);
    }

    public void evictAllReviewsByCourseTitle(String courseTitle){
        courseTitleCache.invalidate(courseTitle);
    }

    public void addAllReviewsByCourseTitle(String courseTitle, List<Review> value){
        courseTitleCache.put(courseTitle, value);
    }
    public List<Review> getAllReviewsByTeacherName(String teacherName){
        return teacherNameCache.getIfPresent(teacherName);
    }
    public void evictAllReviewsByTeacherName(String teacherName){
        teacherNameCache.invalidate(teacherName);
    }
    public void addAllReviewsByTeacherName(String teacherName, List<Review> value){
        teacherNameCache.put(teacherName, value);
    }
}
