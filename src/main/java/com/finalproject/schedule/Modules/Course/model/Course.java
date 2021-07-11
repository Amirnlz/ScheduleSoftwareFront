package com.finalproject.schedule.Modules.Course.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;

@Entity
@Table(name = "course_tbl")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "coursenumber")
public class Course {

    @Id
    @Column(name = "coursenumber")
    private int CourseNumber;

    @Column(name = "title", unique = true)
    private String Title;
    @Column(name = "unitscount")
    private int UnitsCount;
    @Column(name = "term")
    private int Term;

    public Course() {
    }

    public int getCourseNumber() {
        return CourseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        CourseNumber = courseNumber;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUnitsCount() {
        return UnitsCount;
    }

    public void setUnitsCount(int unitsCount) {
        UnitsCount = unitsCount;
    }

    public int getTerm() {
        return Term;
    }

    public void setTerm(int term) {
        Term = term;
    }

}

