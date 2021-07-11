package com.finalproject.schedule.Modules.Master.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.User.model.User;

import javax.persistence.*;

@Entity
@Table(name = "master_course_tbl")
public class MasterCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @OneToOne
    private Course course;

    @JoinColumn
    @OneToOne
    private User user;

    public MasterCourse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
