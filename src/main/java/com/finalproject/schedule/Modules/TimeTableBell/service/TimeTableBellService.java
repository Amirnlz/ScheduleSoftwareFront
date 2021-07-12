package com.finalproject.schedule.Modules.TimeTableBell.service;

import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import com.finalproject.schedule.Modules.TimeTable.repository.TimeTableRepository;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.TimeTableBell.repository.TimeTableBellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableBellService {

    private TimeTableBellRepository timetablebellRepository;
    private TimeTableRepository timetableRepository;

    @Autowired
    public TimeTableBellService(TimeTableBellRepository timetablebellRepository, TimeTableRepository timetableRepository) {
        this.timetablebellRepository = timetablebellRepository;
        this.timetableRepository = timetableRepository;
    }

    public TimeTableBell addTimeTableBell(TimeTableBell timeTableBell){
       return this.timetablebellRepository.save(timeTableBell);
    }
    public List<TimeTableBell> findAllTimeTableBell(){
        return  this.timetablebellRepository.findAll();
    }

    public TimeTableBell findById(int id){
        return  timetablebellRepository.findById(id);
    }

    public void deleteById(int id){
        for (TimeTable timetable:timetableRepository.findAll()){
            if(timetable.getTimetablebell().getId() == id){
                timetableRepository.deleteById(timetable.getId());
            }
        }
        timetablebellRepository.deleteById(id);
    }

    public  List<TimeTableBell>findBellnumber(String day){
        List<TimeTableBell>temp=new ArrayList<>();
        for (TimeTableBell timetablebell:timetablebellRepository.findAll()){
            if(timetablebell.getDay().getLabel().contentEquals(day)){
                temp.add(timetablebell);
            }
        }
        return temp;
    }

    public List<TimeTableBell>findPaginated(int pageNumber, int pageSize){
        Pageable paging= PageRequest.of(pageNumber,pageSize);
        Page<TimeTableBell> page=timetablebellRepository.findAll(paging);
        return page.toList();
    }

}
