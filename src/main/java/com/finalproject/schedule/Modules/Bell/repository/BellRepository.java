package com.finalproject.schedule.Modules.Bell.repository;

import com.finalproject.schedule.Modules.Bell.model.Bell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BellRepository extends JpaRepository<Bell, Integer> {

    Bell findById(int id);
    Bell deleteById(int id);

}
