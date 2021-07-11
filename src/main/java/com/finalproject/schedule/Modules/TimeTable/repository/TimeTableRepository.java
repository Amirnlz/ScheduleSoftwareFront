package com.finalproject.schedule.Modules.TimeTable.repository;

import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

   TimeTable deleteById(int id);
   TimeTable findById(int id);

}
