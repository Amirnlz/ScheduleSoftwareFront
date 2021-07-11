package com.finalproject.schedule.Modules.Course.service;

import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.Course.repository.CourseRepository;
import com.finalproject.schedule.Modules.Master.model.MasterCourse;
import com.finalproject.schedule.Modules.Master.service.MasterCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finalproject.schedule.Modules.Master.repository.MasterCourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private MasterCourseRepository mastercourseRepository;
    private MasterCourseService mastercourseService;

    @Autowired
    public CourseService(CourseRepository courseRepository, MasterCourseRepository mastercourseRepository, MasterCourseService mastercourseService) {
        this.courseRepository = courseRepository;
        this.mastercourseRepository = mastercourseRepository;
        this.mastercourseService = mastercourseService;
    }

    @Transactional /* add course */
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    /* find all courses and return in a list */
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    /* find course by id */
    public Course findById(int id){
        return courseRepository.findById(id);
    }

    /* delete course by id ---> master choose course after admin added so before admin delete course master should delete */
    public void deleteById(int id){
        for (MasterCourse mastercourse : mastercourseRepository.findAll()){
            if(mastercourse.getCourse().getCourseNumber() == id){
                mastercourseService.deleteById(mastercourse.getId());
            }
        }
        courseRepository.deleteById(id);
    }

    public List<Course>findByUnitCounts(int count){
        List<Course>courseList=courseRepository.findAll();
        List<Course>foundedCourses=new ArrayList<>();
        for(Course course:courseList){
            if(course.getUnitsCount()==count)
                foundedCourses.add(course);
        }
        return foundedCourses;
    }

    public List<Course>findByTitle(String title){

        List<Course>courseList=new ArrayList<>();
        for(Course course:courseRepository.findAll()){
            if(course.getTitle().contains(title))
                courseList.add(course);
        }
        return courseList;
    }

    public List<Course>findPaginated(int pageNumber, int pageSize){
        Pageable paging= PageRequest.of(pageNumber,pageSize);
        Page<Course> page=courseRepository.findAll(paging);
        return page.toList();
    }

}
