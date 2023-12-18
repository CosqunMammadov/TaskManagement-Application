package com.example.taskmanagement.dao.repository;

import com.example.taskmanagement.dao.entity.UnverifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnverifiedUserRepository extends JpaRepository<UnverifiedUser, Long> {

}
