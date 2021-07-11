package com.finalproject.schedule.Modules.TimeTableBell.restcontroller;

import com.finalproject.schedule.Modules.Bell.model.Bell;
import com.finalproject.schedule.Modules.Bell.service.BellService;
import com.finalproject.schedule.Modules.Day.model.Day;
import com.finalproject.schedule.Modules.Day.service.DayService;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.TimeTableBell.service.TimeTableBellService;
import com.finalproject.schedule.Modules.User.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/TimeTableBells")
public class TimeTableBellRestController {

    DayService dayService;
    BellService bellService;
    TimeTableBellService timeTableBellService;

    @Autowired
    public TimeTableBellRestController(DayService dayService, BellService bellService, TimeTableBellService timeTableBellService) {
        this.dayService = dayService;
        this.bellService = bellService;
        this.timeTableBellService = timeTableBellService;
    }

    @PostMapping(value = "")
    public ResponseEntity setTimeTable(@RequestParam int dayId, @RequestParam int bellId){

        Day day=dayService.findById(dayId);
        Bell bell=bellService.findById(bellId);
        if(day!=null&&bell!=null){
            System.out.println("not null");
            TimeTableBell timeTableBell=new TimeTableBell();
            timeTableBell.setBell(bell);
            timeTableBell.setDay(day);
            timeTableBellService.addTimeTableBell(timeTableBell);
        }

        return  ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
    public PageModel getTimeTableBellPage(@RequestParam int pageSie, @RequestParam int pageNumber){

        int total=timeTableBellService.findAllTimeTableBell().size();

        PageModel pageModel=new PageModel();
        pageModel.setPageSize(pageSie);
        pageModel.setPageNumber(pageNumber);
        pageModel.setTotalPages(total/pageSie);
        pageModel.setList(timeTableBellService.findPaginated(pageNumber,pageSie));
        return  pageModel;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findTimeTableBellById(@PathVariable("id")int id){
        Optional<TimeTableBell> foundedTimeTableBell = Optional.ofNullable(timeTableBellService.findById(id));
        return foundedTimeTableBell.map(response-> ResponseEntity.ok().body(response)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TimeTableBell> deletebyId(@PathVariable int id){
        timeTableBellService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
