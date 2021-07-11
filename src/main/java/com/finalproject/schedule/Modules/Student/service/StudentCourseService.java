package com.finalproject.schedule.Modules.Student.service;

import com.finalproject.schedule.Modules.Student.model.StudentCourse;
import com.finalproject.schedule.Modules.Student.repository.StudentCourseRepository;
import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentCourseService {

    private StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    public StudentCourse addCourse(StudentCourse studentCourse) {
        return studentCourseRepository.save(studentCourse);
    }

    public List<StudentCourse> findAllStudentCourses() {
        return studentCourseRepository.findAll();
    }

    public StudentCourse findById(int id){
        return studentCourseRepository.findById(id);
    }

    public void deleteById(int id){
        studentCourseRepository.deleteById(id);
    }

    public  List<StudentCourse>findtimetablenumber(String day){
        List<StudentCourse>temp=new ArrayList<>();
        for (StudentCourse studentcourse:studentCourseRepository.findAll()){
            if(studentcourse.getTimetable().getTimetablebell().getDay().getLabel().contentEquals(day)){
                temp.add(studentcourse);
            }
        }
        return temp;
    }

    public List<StudentCourse>findstudentnumber(TimeTable timetable){
        List<StudentCourse>temp=new ArrayList<>();
        for (StudentCourse studentcourse:studentCourseRepository.findAll()){
            if(studentcourse.getTimetable().equals(timetable)){
                temp.add(studentcourse);
            }
        }
        return temp;
    }


}
