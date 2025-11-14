package com.todoteam.todolist.dto;

import com.todoteam.todolist.model.Difficulty;
import com.todoteam.todolist.model.Step;

import java.util.List;

public class CreateTaskRequest
{
    public String title;
    public Difficulty difficulty;
    public List<StepDTO> steps;
}
