package com.example.taskmanagement.dao.repository;

import com.example.taskmanagement.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username =:name OR u.email =:name")
    User getUserByUsernameOrEmail(String name);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.sessionId = :sessionId")
    User getBySessionId(String sessionId);
}
