package com.finalproject.schedule.Modules.Announcements.repository;

import com.finalproject.schedule.Modules.Announcements.model.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Integer> {

    Announce findById(int id);
    Announce deleteById(int id);

}
