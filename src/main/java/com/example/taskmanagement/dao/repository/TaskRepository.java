package com.example.taskmanagement.dao.repository;

import com.example.taskmanagement.dao.entity.Task;
import com.example.taskmanagement.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.category WHERE t.category.id = :categoryId")
    Set<Task> getByCategoryId(Long categoryId);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.category WHERE t.status = :status")
    Set<Task> getByStatus(TaskStatus status);
}
