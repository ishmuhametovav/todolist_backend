package com.todoteam.todolist.repository.impl;

import com.todoteam.todolist.model.Task;
import com.todoteam.todolist.repository.TaskDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskDAOImpl implements TaskDAO
{
    private final ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong genId = new AtomicLong(0);

    @Override
    public Task addTask(Task task)
    {
        Long id = task.getId();
        if(id == null)
        {
            id = genId.getAndIncrement();
            task.setId(id);
        }

        Task prev = tasks.putIfAbsent(id, task);

        if(prev != null)
        {
            return null;
        }

        return task;
    }

    @Override
    public Task findTaskById(Long id)
    {
        return tasks.get(id);
    }

    @Override
    public List<Task> findTasksByUserId(Long userId)
    {
        return tasks.values().stream().filter(task -> task.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void updateTask(Task task)
    {
        Long id = task.getId();

        Objects.requireNonNull(id, "id must not be null");

        tasks.put(id, task);
    }

    @Override
    public Task deleteTask(Long id)
    {
        return tasks.remove(id);
    }
}
