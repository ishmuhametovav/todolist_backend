package com.todoteam.todolist.service;

import com.todoteam.todolist.model.Step;
import com.todoteam.todolist.model.Task;

import java.util.List;

public interface TaskService
{
    Task addTask(Task task);

    Task findTaskById(Long id);

    List<Task> findTasksByUserId(Long userId);

    void updateTask(Task task);

    Task deleteTask(Long id);
}
