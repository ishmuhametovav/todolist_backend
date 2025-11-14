package com.todoteam.todolist.service.impl;

import com.todoteam.todolist.model.Step;
import com.todoteam.todolist.model.Task;
import com.todoteam.todolist.service.StepService;
import com.todoteam.todolist.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class InMemoryStepServiceImpl implements StepService
{
    private final TaskService taskService;

    @Override
    public Step addStep(Long taskId, Step step)
    {
        Objects.requireNonNull(taskId, "taskId must not be null!");
        Objects.requireNonNull(step, "step must not be null!");

        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");

        List<Step> steps = task.getSteps();

        steps.add(step);

        return step;
    }

    @Override
    public List<Step> findStepsByTaskId(Long taskId)
    {
        Objects.requireNonNull(taskId, "taskId must not be null!");
        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");
        return task.getSteps();
    }

    @Override
    public Step findStepById(Long taskId, Long id)
    {
        Objects.requireNonNull(taskId, "taskId must not be null!");
        Objects.requireNonNull(id, "id must not be null!");

        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");

        List<Step> steps = task.getSteps();

        int index = IntStream.range(0, steps.size())
                .filter(indx -> steps.get(indx).getId().equals(id))
                .findFirst().orElse(-1);

        if(index == -1)
        {
            throw new IllegalArgumentException("No step with such id");
        }

        return steps.get(index);
    }

    @Override
    public void updateStep(Long taskId, Step step)
    {
        Objects.requireNonNull(taskId, "taskId must not be null!");
        Objects.requireNonNull(step, "step must not be null!");

        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");
        List<Step> steps = task.getSteps();

        int index = IntStream.range(0, steps.size())
                .filter(indx -> steps.get(indx).getId().equals(step.getId()))
                .findFirst().orElse(-1);

        if(index == -1)
        {
            throw new IllegalArgumentException("No such step");
        }
        steps.set(index, step);
    }

    @Override
    public Step deleteStep(Long taskId, Long id)
    {
        Objects.requireNonNull(taskId, "taskId must not be null!");
        Objects.requireNonNull(id, "id must not be null!");

        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");

        List<Step> steps = task.getSteps();

        Step step = steps.stream().filter(stp -> stp.getId().equals(id))
                .findFirst().orElse(null);

        if(step == null)
        {
            throw new IllegalArgumentException("Step with such id does not exist!");
        }

        steps.remove(step);

        return step;
    }

    @Override
    public Step moveStep(Long taskId, Long id, int newPosition)
    {
        Task task = taskService.findTaskById(taskId);
        Objects.requireNonNull(task, "no task with such id!");
        List<Step> steps = task.getSteps();

        int oldMasPosition = IntStream.range(0, steps.size())
                .filter(index->steps.get(index).getId().equals(id))
                .findFirst().orElse(-1);

        if(oldMasPosition == -1)
        {
            throw new IllegalArgumentException("step with such id does not exist!");
        }

        int newMasPosition = IntStream.range(0, steps.size())
                .filter(index->steps.get(index).getPosition().equals(newPosition))
                .findFirst().orElse(-1);

        if(newMasPosition == -1)
        {
            throw new IllegalArgumentException("step with such position does not exist!");
        }

        int oldPosition = steps.get(oldMasPosition).getPosition();

        Step step = steps.get(oldMasPosition);
        Step swap = steps.get(newMasPosition);

        step.setPosition(newPosition);
        swap.setPosition(oldPosition);

        return swap;
    }
}
