package com.todoteam.todolist.repository;

import com.todoteam.todolist.model.User;

import java.util.List;

public interface UserDAO
{
    User addUser(User user);

    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByName(String name);

    void updateUser(User user);

    User deleteUser(Long id);
}
