package com.finalproject.schedule.Modules.Day.service;

import com.finalproject.schedule.Modules.Day.model.Day;
import com.finalproject.schedule.Modules.Day.repository.DayRepository;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.TimeTableBell.repository.TimeTableBellRepository;
import com.finalproject.schedule.Modules.TimeTableBell.service.TimeTableBellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class DayService {

    private DayRepository dayRepository;
    private TimeTableBellRepository timetablebellRepository;
    private TimeTableBellService timetablebellService;

    @Autowired
    public DayService(DayRepository dayRepository, TimeTableBellRepository timetablebellRepository, TimeTableBellService timetablebellService) {
        this.dayRepository = dayRepository;
        this.timetablebellRepository = timetablebellRepository;
        this.timetablebellService = timetablebellService;
    }

    @Transactional
    public Day addDay(Day day) {
        return this.dayRepository.save(day);
    }

    public List<Day> findAllDays() {
        return this.dayRepository.findAll();
    }

    public  Day findById(int id){
        return  dayRepository.findById(id);
    }

    public void deleteById(int id){
        for (TimeTableBell timetablebell:timetablebellRepository.findAll()){
            if(timetablebell.getDay().getId() == id){
                timetablebellService.deleteById(timetablebell.getId());
            }
        }
        dayRepository.deleteById(id);
    }

    public List<Day>findPaginated(int pageNumber, int pageSize){
        Pageable paging= PageRequest.of(pageNumber,pageSize);
        Page<Day> page=dayRepository.findAll(paging);
        return page.toList();
    }

}

