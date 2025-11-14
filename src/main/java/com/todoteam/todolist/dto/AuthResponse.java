package com.todoteam.todolist.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse
{
    public String token;
    public Long userId;
    public String name;
}
