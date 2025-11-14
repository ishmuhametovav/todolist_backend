package com.todoteam.todolist.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Step
{
    private Long id;
    @NonNull
    private String description;
    private Integer position;
    private Boolean completed;
}
