package com.finalproject.schedule.Modules.Student.repository;

import com.finalproject.schedule.Modules.Student.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {

    StudentCourse findById(int id);
    void deleteById(int id);

}
