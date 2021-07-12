package com.finalproject.schedule.Modules.User.repository;

import com.finalproject.schedule.Modules.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findById(int id);
    User findByCode(String code);
    void deleteById(int id);

}
