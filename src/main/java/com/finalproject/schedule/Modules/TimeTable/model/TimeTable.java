package com.finalproject.schedule.Modules.TimeTable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.User.model.User;

import javax.persistence.*;

@Entity
@Table(name = "timetable_tbl")
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    @OneToOne
    private User user;

    @JoinColumn
    @OneToOne
    private TimeTableBell timetablebell;

    @JoinColumn
    @OneToOne
    private Course course;

    /*
    * 0 --> accept 👌
    * 1 --> not accept ⛔
    * 2 --> waiting ⌚
    */
    private int acceptance;

    private String reason;

    @Transient
    @JsonIgnore
    private int classnumber;


    public TimeTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TimeTableBell getTimetablebell() {
        return timetablebell;
    }

    public void setTimetablebell(TimeTableBell timetablebell) {
        this.timetablebell = timetablebell;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(int acceptance) {
        this.acceptance = acceptance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getClassnumber() {
        return classnumber;
    }

    public void setClassnumber(int classnumber) {
        this.classnumber = classnumber;
    }



}
