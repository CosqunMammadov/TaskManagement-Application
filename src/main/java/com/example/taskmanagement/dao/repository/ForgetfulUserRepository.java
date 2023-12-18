package com.example.taskmanagement.dao.repository;

import com.example.taskmanagement.dao.entity.ForgetfulUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ForgetfulUserRepository extends JpaRepository<ForgetfulUser, Long> {

    @Query("DELETE FROM ForgetfulUser fu WHERE fu.expirationDateForVerify < :currentDate OR fu.expirationDateForChange < :currentDate")
    void deleteExpiredUsers(LocalDateTime currentDate);
}
