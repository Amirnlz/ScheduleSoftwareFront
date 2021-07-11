package com.finalproject.schedule.Modules.TimeTable.restcontroller;

import com.finalproject.schedule.Modules.Course.model.Course;
import com.finalproject.schedule.Modules.Student.model.StudentCourse;
import com.finalproject.schedule.Modules.Student.service.StudentCourseService;
import com.finalproject.schedule.Modules.TimeTable.model.TimeTable;
import com.finalproject.schedule.Modules.TimeTable.service.TimeTableService;
import com.finalproject.schedule.Modules.TimeTableBell.model.TimeTableBell;
import com.finalproject.schedule.Modules.User.model.PageModel;
import com.finalproject.schedule.Modules.User.model.User;
import com.finalproject.schedule.Modules.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TimeTableRestController {

    private TimeTableService timetableService;
    private UserService userService;
    private StudentCourseService studentCourseService;

    static List<TimeTableBell> timetablebellList = new ArrayList<>();
    static List<Integer> timetablebellsizeList = new ArrayList<>();

    @Autowired
    public TimeTableRestController(TimeTableService timetableService, UserService userService, StudentCourseService studentCourseService) {
        this.timetableService = timetableService;
        this.userService = userService;
        this.studentCourseService = studentCourseService;
    }

    /*
    http://localhost:8585/api/TimeTables
    GET
    1.گرفتن تمام timetable ها
    2.جستجو بر اساس CourseId
    3.جستجو بر اساس masterId
    4.جستجو بر اساس studentId
    */
    @RequestMapping(value = "/api/TimeTables", method = RequestMethod.GET)
    public PageModel getTimeTablePage(@RequestParam int studentId, @RequestParam int CourseId, @RequestParam int masterId,@RequestParam int pageNumber, @RequestParam int pageSie){

        int total=timetableService.findAllTimeTables().size();
        List<TimeTable> foundedByStudent=timetableService.findByStudentId(studentId);
        List<TimeTable> foundedByCourse=timetableService.findByCourseId(CourseId);
        List<TimeTable> foundedByMaster=timetableService.findByMasterId(masterId);
        List<TimeTable> totalList=new ArrayList<>();
        totalList.addAll(foundedByStudent);
        totalList.addAll(foundedByCourse);
        totalList.addAll(foundedByMaster);

        for(int i=0;i<totalList.size();i++){
            for(int j=i+1;j<totalList.size();j++)
                if(totalList.get(i).equals(totalList.get(j)))
                    totalList.remove(j);
        }

        PageModel pageModel=new PageModel();

        if(!totalList.isEmpty()){
            pageModel.setPageSize(pageSie);
            pageModel.setPageNumber(pageNumber);
            pageModel.setTotalPages((int) Math.ceil((double) totalList.size()/(double) pageSie));
            if(pageSie<=totalList.size())
                pageModel.setList(totalList.subList((pageNumber)*pageSie,(pageNumber+1)*pageSie));
            else
                pageModel.setList(totalList);
            return  pageModel;
        }

        pageModel.setPageSize(pageSie);
        pageModel.setPageNumber(pageNumber);
        pageModel.setTotalPages((int) Math.ceil((double) total/(double)pageSie));
        pageModel.setList(timetableService.findPaginated(pageNumber,pageSie));
        return  pageModel;
    }

    /*
    http://localhost:8585/api/TimeTables/id
    GET
    */
    @RequestMapping(value = "/api/TimeTables/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findTimeTableById(@RequestParam int id){
        Optional<TimeTable> foundedTimeTable= Optional.ofNullable(timetableService.findById(id));
        return foundedTimeTable.map(response-> ResponseEntity.ok().body(response)).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*
    http://localhost:8585/api/TimeTableChoose/id/Choose
    POST
    دانشجو انتخاب واحد می کند
    */
    @RequestMapping(value = "/api/TimeTableChoose/{id}/Choose", method = RequestMethod.POST)
    public ResponseEntity setStudentCourse(@PathVariable("id") int id) {

        TimeTable timeTable = timetableService.findById(id);
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setUser(user);
        studentCourse.setTimetable(timeTable);
        studentCourseService.addCourse(studentCourse);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/api/TimeTables/StartProcess", method = RequestMethod.POST)
    public ResponseEntity startProccess(@RequestParam int maxClassPerBell) throws IllegalAccessException, IOException, InvocationTargetException {

        List<TimeTable> timetableList = timetableService.findAllTimeTables();

        /* 0 class */
        if(maxClassPerBell == 0){
            for(int i=0; i < timetableList.size() ; i++) {
                timetableList.get(i).setAcceptance(0);
                timetableList.get(i).setReason("تعداد کلاس های آماده برای تدریس در دانشگاه صفر می باشد!");
                timetableService.registerTimeTable(timetableList.get(i));
            }
        }

        /* more than 0 class */
        else{
            /* set all acceptance 1 */
            for(int i=0; i<timetableList.size() ; i++) {
                timetableList.get(i).setAcceptance(1);
                timetableList.get(i).setReason("");
                timetableService.registerTimeTable(timetableList.get(i));
            }

            /* find conflicts and set 0 */
            for(int i=0; i < timetableList.size() ; i++){
                for (int j=i+1; j < timetableList.size() ; j++){

                    //TimeTableBell Motafavet -- Day Barabar -- Bell Motafavet -- Course Barabar
                    if(!timetableList.get(i).getTimetablebell().equals(timetableList.get(j).getTimetablebell())){
                        if(timetableList.get(i).getTimetablebell().getDay().equals(timetableList.get(j).getTimetablebell().getDay()) && !timetableList.get(i).getTimetablebell().getBell().equals(timetableList.get(j).getTimetablebell().getBell())){
                            if(timetableList.get(i).getCourse().equals(timetableList.get(j).getCourse())){
                                if(coursesize(timetableList.get(i).getCourse(),timetableList.get(i).getTimetablebell()) >= coursesize(timetableList.get(j).getCourse(),timetableList.get(j).getTimetablebell())){
                                    timetableList.get(j).setAcceptance(0);
                                    timetableList.get(j).setReason(" * یک درس در دانشگاه در یک روز در دو زنگ جدا نمی تواند تشکیل شود بنابر بر قوانین سیستم اولویت با جدول زمانی "+timetableList.get(i).getTimetablebell().getDay().getLabel()+" / "+timetableList.get(i).getTimetablebell().getBell().getLabel()+" است! ");
                                    timetableService.registerTimeTable(timetableList.get(j));}
                                else{
                                    timetableList.get(i).setAcceptance(0);
                                    timetableList.get(i).setReason(" * یک درس در دانشگاه در یک روز در دو زنگ جدا نمی تواند تشکیل شود بنابر بر قوانین سیستم اولویت با جدول زمانی "+timetableList.get(j).getTimetablebell().getDay().getLabel()+" / "+timetableList.get(j).getTimetablebell().getBell().getLabel()+" است! ");
                                    timetableService.registerTimeTable(timetableList.get(i));
                                }

                            }
                        }
                    }
                    //TimeTableBell Barabar -- Term Barabar -- Dars Motafavet
                    if(timetableList.get(i).getTimetablebell().equals(timetableList.get(j).getTimetablebell())){
                        if(timetableList.get(i).getCourse().getTerm() == timetableList.get(j).getCourse().getTerm() && timetableList.get(i).getCourse().getCourseNumber() != timetableList.get(j).getCourse().getCourseNumber()){
                            if(timetablebellsize(timetableList.get(i).getTimetablebell(),timetableList.get(i).getCourse()) >= timetablebellsize(timetableList.get(j).getTimetablebell(),timetableList.get(j).getCourse())){
                                timetableList.get(j).setAcceptance(0);
                                timetableList.get(j).setReason(" * در یک روز و ساعت دو درس با ترم های برابر نمی تواند تشکیل شود بنابر بر قوانین دانشگاه اولویت با جدول زمانی "+timetableList.get(i).getTimetablebell().getDay().getLabel()+"/"+timetableList.get(i).getTimetablebell().getBell().getLabel()+" است! ");
                                timetableService.registerTimeTable(timetableList.get(j));
                            }
                            else{
                                timetableList.get(i).setAcceptance(0);
                                timetableList.get(i).setReason(" * در یک روز و ساعت دو درس با ترم های برابر نمی تواند تشکیل شود بنابر بر قوانین دانشگاه اولویت با جدول زمانی "+timetableList.get(j).getTimetablebell().getDay().getLabel()+"/"+timetableList.get(j).getTimetablebell().getBell().getLabel()+" است! ");
                                timetableService.registerTimeTable(timetableList.get(i));
                            }
                        }
                    }
                }
            }

            /* few class */
            for(int i=0; i < timetableList.size() ; i++){

                if(!timetablebellList.contains(timetableList.get(i).getTimetablebell())){
                    timetablebellList.add(timetableList.get(i).getTimetablebell());
                    timetablebellsizeList.add(1);
                }
                else{
                    for (int j=0; j<timetablebellList.size() ; j++){
                        if(timetablebellsizeList.get(j) >= maxClassPerBell && timetableList.get(i).getTimetablebell().equals(timetablebellList.get(j))){
                            if(timetableList.get(i).getAcceptance() != 0){
                                timetableList.get(i).setAcceptance(0);
                                timetableList.get(i).setReason("کمبود کلاس");
                                timetableService.registerTimeTable(timetableList.get(i));
                            }
                        }
                        else if(timetablebellsizeList.get(j) < maxClassPerBell && timetableList.get(i).getTimetablebell().equals(timetablebellList.get(j))){
                            timetablebellsizeList.set(j,timetablebellsizeList.get(j)+1);
                        }
                    }

                }
            }
        }

        return ResponseEntity.ok().build();
    }

    public int timetablebellsize(TimeTableBell timetablebell, Course course){
        List<TimeTable> timetableList = timetableService.findAllTimeTables();
        int number = 0;
        for(int i=0; i < timetableList.size() ; i++){
            if(timetableList.get(i).getTimetablebell().equals(timetablebell) && timetableList.get(i).getCourse().equals(course)){
                number++;
            }
        }
        return number;
    }

    public int coursesize(Course course,TimeTableBell timetablebell){
        List<TimeTable> timetableList = timetableService.findAllTimeTables();
        int number = 0;
        for(int i=0; i < timetableList.size() ; i++){
            if(timetableList.get(i).getCourse().equals(course) && timetableList.get(i).getTimetablebell().equals(timetablebell)){
                number++;
            }
        }
        return number;
    }

}
