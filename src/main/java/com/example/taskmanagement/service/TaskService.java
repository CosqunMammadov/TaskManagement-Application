package com.example.taskmanagement.service;

import com.example.taskmanagement.dao.entity.Task;
import com.example.taskmanagement.dao.repository.TaskRepository;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.model.enums.TaskPriority;
import com.example.taskmanagement.model.enums.TaskStatus;
import com.example.taskmanagement.model.request.TaskRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task add(TaskRequestDTO taskRequestDTO){
        Task task = taskMapper.taskRequestDtoToTask(taskRequestDTO);
        return taskRepository.save(task);
    }

    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public Set<Task> getByCategoryId(Long categoryId){
        return taskRepository.getByCategoryId(categoryId);
    }

    public Set<Task> getByStatus(TaskStatus status){
        return taskRepository.getByStatus(status);
    }

    public Task changeStatus(Long id, TaskStatus status){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Task not found. Id: %d", id)));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task changePriority(Long id, TaskPriority priority){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Task not found. Id: %d", id)));
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    public Task markAsCompleted(Long id){
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Task not found. Id: %d", id)));
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
