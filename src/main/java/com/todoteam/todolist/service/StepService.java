package com.todoteam.todolist.service;

import com.todoteam.todolist.model.Step;

import java.util.List;

public interface StepService
{
    Step addStep(Long taskId, Step step);

    List<Step> findStepsByTaskId(Long taskId);

    Step findStepById(Long taskId, Long id);

    void updateStep(Long taskId, Step step);

    Step deleteStep(Long taskId, Long id);

    Step moveStep(Long taskId, Long id, int newPosition);
}
