package com.finalproject.schedule.Modules.Day.repository;

import com.finalproject.schedule.Modules.Day.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {

    Day findById(int id);
    void deleteById(int id);

}
