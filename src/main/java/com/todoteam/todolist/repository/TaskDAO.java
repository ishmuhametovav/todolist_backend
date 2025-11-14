package com.todoteam.todolist.repository;

import com.todoteam.todolist.model.Task;

import java.util.List;

public interface TaskDAO
{
    Task addTask(Task task);

    Task findTaskById(Long id);

    List<Task> findTasksByUserId(Long userId);

    void updateTask(Task task);

    Task deleteTask(Long id);
}
