package com.todoteam.todolist.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Task
{
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private String title;
    @NonNull
    private Difficulty difficulty;

    private Boolean completed;

    private List<Step> steps = new ArrayList<>();
}
