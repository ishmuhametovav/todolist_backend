package com.todoteam.todolist.service.impl;

import com.todoteam.todolist.model.Step;
import com.todoteam.todolist.model.Task;
import com.todoteam.todolist.repository.TaskDAO;
import com.todoteam.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class InMemoryTaskServiceImpl implements TaskService
{
    private final TaskDAO taskDAO;

    @Override
    public Task addTask(Task task)
    {
        Objects.requireNonNull(task, "task must not be null!");
        Task result = taskDAO.addTask(task);

        if(result == null)
        {
            throw new IllegalArgumentException("Task already exist!");
        }
        return result;
    }

    @Override
    public Task findTaskById(Long id)
    {
        Objects.requireNonNull(id, "id must not be null!");
        Task result = taskDAO.findTaskById(id);
        if(result == null)
        {
            throw new IllegalArgumentException("Task with such id does not exist!");
        }
        return result;
    }

    @Override
    public List<Task> findTasksByUserId(Long userId)
    {
        Objects.requireNonNull(userId, "userId must not be null!");
        return taskDAO.findTasksByUserId(userId);
    }

    @Override
    public void updateTask(Task task)
    {
        Objects.requireNonNull(task, "task must not be null!");
        taskDAO.updateTask(task);
    }

    @Override
    public Task deleteTask(Long id)
    {
        Objects.requireNonNull(id, "id must not be null!");
        return taskDAO.deleteTask(id);
    }
}
