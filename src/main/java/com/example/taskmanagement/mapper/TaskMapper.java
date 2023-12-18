package com.example.taskmanagement.mapper;

import com.example.taskmanagement.dao.entity.Task;
import com.example.taskmanagement.model.request.TaskRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task taskRequestDtoToTask(TaskRequestDTO taskRequestDTO);
    TaskRequestDTO taskToTaskRequestDto(Task task);
}
