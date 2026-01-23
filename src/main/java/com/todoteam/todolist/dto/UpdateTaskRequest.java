package com.todoteam.todolist.dto;

import com.todoteam.todolist.model.Difficulty;
import com.todoteam.todolist.model.Step;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateTaskRequest
{
    public String title;

    public Difficulty difficulty;

    public List<StepDTO> steps;

    public Boolean completed;

    public LocalDateTime createdAt;
}
