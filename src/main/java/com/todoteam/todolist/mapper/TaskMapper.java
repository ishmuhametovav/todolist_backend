package com.todoteam.todolist.mapper;

import com.todoteam.todolist.dto.CreateTaskRequest;
import com.todoteam.todolist.dto.StepDTO;
import com.todoteam.todolist.dto.TaskResponse;
import com.todoteam.todolist.dto.UpdateTaskRequest;
import com.todoteam.todolist.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper
{
    public static TaskResponse toResponse(Task task)
    {
        if(task == null) return null;

        List<StepDTO> steps = task.getSteps() == null
                ? List.of()
                : StepMapper.toResponseList(task.getSteps());


        return new TaskResponse(task.getId(), task.getUserId(),
                task.getTitle(), task.getDifficulty(), task.getCompleted(), steps);
    }

    public static List<TaskResponse> toResponseList(List<Task> tasks)
    {
        if (tasks == null) return List.of();
        return tasks.stream().map(TaskMapper::toResponse).collect(Collectors.toList());
    }

    public static Task fromCreateRequestToTask(CreateTaskRequest request, Long userId)
    {
        Task t = Task.builder().userId(userId).title(request.title)
                .difficulty(request.difficulty).build();

        t.setSteps(StepMapper.fromUpdateRequestToStepList(request.steps));

        return t;
    }

    public static Task fromUpdateRequestToTask(UpdateTaskRequest request, Long userId)
    {
        Task t = Task.builder().userId(userId).title(request.title)
                .difficulty(request.difficulty).completed(request.completed).build();

        t.setSteps(StepMapper.fromUpdateRequestToStepList(request.steps));

        return t;
    }
}
