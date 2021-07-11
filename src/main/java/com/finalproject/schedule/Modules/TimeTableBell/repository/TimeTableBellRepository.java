package com.finalproject.schedule.Modules.TimeTableBell.repository;

import com.finalproject.schedule.Modules.Day.model.Day;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableBellRepository extends JpaRepository<TimeTableBell,Integer> {

    TimeTableBell findById(int id);
    void deleteById(int id);

}
