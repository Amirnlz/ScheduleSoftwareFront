package com.finalproject.schedule.Modules.Announcements.service;

import com.finalproject.schedule.Modules.Announcements.model.Announce;
import com.finalproject.schedule.Modules.Announcements.repository.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnnounceService {

    private AnnounceRepository announceRepository;

    @Autowired
    public AnnounceService(AnnounceRepository announceRepository) {
        this.announceRepository=announceRepository;
    }

    @Transactional /* add announce */
    public Announce addAnnounce(Announce announce) {
        return this.announceRepository.save(announce);
    }

    /* find all announces and return in a list */
    public List<Announce> findAllAnnounce() {
        return this.announceRepository.findAll();
    }

    /* find announce by id */
    public Announce findById(int id){
        return this.announceRepository.findById(id);
    }

    /* delete announce by id */
    public Announce deleteById(int id){
        return  this.announceRepository.deleteById(id);
    }

    /* find announce by TimeTableId */
    public List<Announce>findByTimeTableId(int id){
        List<Announce>announceList=announceRepository.findAll();
        List<Announce>foundedAnnounces=new ArrayList<>();
        for(Announce announce:announceList){
            if(announce.getTimeTable().getId()==id)
                foundedAnnounces.add(announce);
        }
        return foundedAnnounces;
    }

    /* find announce by MasterId */
    public List<Announce>findByMasterId(int masterId){

        List<Announce>announceList=new ArrayList<>();
        for(Announce announce:announceRepository.findAll()){
            if(announce.getTimeTable().getUser().getId()==masterId)
                announceList.add(announce);
        }
        return announceList;
    }

    /* Page */
    public List<Announce>findPaginated(int pageNumber, int pageSize){
        Pageable paging= PageRequest.of(pageNumber,pageSize);
        Page<Announce> page=announceRepository.findAll(paging);
        return page.toList();
    }


}


