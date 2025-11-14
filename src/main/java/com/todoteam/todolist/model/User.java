package com.todoteam.todolist.model;

import lombok.Data;
import lombok.Builder;
import lombok.NonNull;

@Data
@Builder
public class User
{
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String hashPassword;
}
