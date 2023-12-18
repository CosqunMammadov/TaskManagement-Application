package com.example.taskmanagement.controller;

import com.example.taskmanagement.dao.entity.Task;
import com.example.taskmanagement.model.enums.TaskPriority;
import com.example.taskmanagement.model.enums.TaskStatus;
import com.example.taskmanagement.model.request.TaskRequestDTO;
import com.example.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    @Operation(summary = "This endpoint help us to add new task")
    public Task add(@RequestBody TaskRequestDTO taskRequestDTO){
        return taskService.add(taskRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @GetMapping("/by-category-id/{categoryId}")
    @Operation(summary = "This endpoint help us to get task by category name")
    public Set<Task> getByCategoryId(@PathVariable Long categoryId){
        return taskService.getByCategoryId(categoryId);
    }

    @GetMapping("/by-status")
    @Operation(summary = "This endpoint help us to get task by it's status")
    public Set<Task> getByStatus(@RequestParam TaskStatus status){
        return taskService.getByStatus(status);
    }

    @PatchMapping("/change-status/{id}")
    @Operation(summary = "This endpoint help us to change task status")
    public Task changeStatus(@PathVariable Long id, @RequestParam TaskStatus taskStatus){
        return taskService.changeStatus(id, taskStatus);
    }

    @PatchMapping("/change-priority/{id}")
    @Operation(summary = "This endpoint help us to change task priority")
    public Task changePriority(@PathVariable Long id, @RequestParam TaskPriority taskPriority){
        return taskService.changePriority(id, taskPriority);
    }

    @PatchMapping("/mark-as-completed/{id}")
    @Operation(summary = "This endpoint help us to mark the task as completed")
    public Task markAsCompleted(@PathVariable Long id){
        return taskService.markAsCompleted(id);
    }
}
