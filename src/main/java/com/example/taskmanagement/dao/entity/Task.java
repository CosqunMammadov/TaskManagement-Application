package com.example.taskmanagement.dao.entity;

import com.example.taskmanagement.model.enums.TaskPriority;
import com.example.taskmanagement.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;

    @Enumerated(EnumType.STRING)
    TaskPriority priority;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
