package com.finalproject.schedule.Modules.Day.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.schedule.Modules.Bell.model.Bell;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;

import javax.persistence.*;

@Entity
@Table(name = "day_tbl")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private int dayOfWeek;
    @Column(unique = true,name = "label")
    private String Label;


    public Day() {
    }

    public Day(int dayOfWeek, String label) {
        this.dayOfWeek = dayOfWeek;
        this.Label = label;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }



}
