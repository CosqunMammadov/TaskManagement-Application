package com.example.taskmanagement.model.request;

import com.example.taskmanagement.model.enums.TaskPriority;
import com.example.taskmanagement.model.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequestDTO {
    String name;
    String description;
    TaskPriority priority;
    TaskStatus status;
    LocalDateTime deadline;
    Long categoryId;
}
