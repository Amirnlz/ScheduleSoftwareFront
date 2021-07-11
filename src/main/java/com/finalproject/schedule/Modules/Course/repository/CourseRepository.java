package com.finalproject.schedule.Modules.Course.repository;

import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findById(int id);
    void deleteById(int id);

}
