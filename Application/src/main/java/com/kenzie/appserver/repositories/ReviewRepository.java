package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ReviewEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ReviewRepository extends CrudRepository<ReviewEntity, String> {

    List<ReviewEntity> findAllByTeacherName(String teacherName);
    List<ReviewEntity> findAllByCourseTitle(String courseTitle);
    List<ReviewEntity> findAllByUsername(String username);


}
