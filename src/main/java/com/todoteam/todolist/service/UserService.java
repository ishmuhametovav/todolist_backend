package com.todoteam.todolist.service;

import com.todoteam.todolist.model.User;

import java.util.List;

public interface UserService
{
    User authenticate(String name, String plainPassword);

    User addUser(String name, String plainPassword);

    List<User> findAllUsers();

    User findUserById(Long id);

    void updateUser(User user);

    User deleteUser(Long id);
}
