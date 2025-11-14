package com.todoteam.todolist.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StepDTO
{
    public Long id;
    public String description;
    public Integer position;
    public Boolean completed;
}
