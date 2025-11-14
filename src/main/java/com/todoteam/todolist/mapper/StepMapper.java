package com.todoteam.todolist.mapper;

import com.todoteam.todolist.dto.StepDTO;
import com.todoteam.todolist.model.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StepMapper
{
    public static StepDTO toResponse(Step step)
    {
        if(step == null) return null;
        return new StepDTO(step.getId(), step.getDescription(),
                step.getPosition(), step.getCompleted());
    }

    public static List<StepDTO> toResponseList(List<Step> steps)
    {
        if(steps == null || steps.isEmpty()) return List.of();
        return steps.stream()
                .filter(Objects::nonNull)
                .map(StepMapper::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public static Step fromUpdateRequestToStep(StepDTO request)
    {
        return Step.builder().id(request.id).description(request.description)
                .position(request.position).completed(request.completed).build();
    }

    public static List<Step> fromUpdateRequestToStepList(List<StepDTO> request)
    {
        List<Step> steps = new ArrayList<>(request.size());
        for(StepDTO stepDto : request)
        {
            steps.add(fromUpdateRequestToStep(stepDto));
        }

        return steps;
    }


}
