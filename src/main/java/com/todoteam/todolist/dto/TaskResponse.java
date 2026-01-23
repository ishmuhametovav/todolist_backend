package com.todoteam.todolist.dto;

import com.todoteam.todolist.model.Difficulty;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class TaskResponse
{
    public Long id;
    public Long userId;
    public String title;
    public Difficulty difficulty;
    public Boolean completed;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public List<StepDTO> steps;
}
