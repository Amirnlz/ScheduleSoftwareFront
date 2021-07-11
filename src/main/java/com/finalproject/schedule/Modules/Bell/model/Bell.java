package com.finalproject.schedule.Modules.Bell.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;

import javax.persistence.*;

@Entity
@Table(name = "bell_tbl")
public class Bell {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private int bellOfDay;
    @Column(unique = true,name = "label")
    private String Label;

    public Bell() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bell(int bellOfDay, String label) {
        this.bellOfDay = bellOfDay;
        this.Label = label;
    }

    public int getBellOfDay() {
        return bellOfDay;
    }

    public void setBellOfDay(int bellOfDay) {
        this.bellOfDay = bellOfDay;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

}
