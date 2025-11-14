package com.todoteam.todolist.repository.impl;

import com.todoteam.todolist.model.User;
import com.todoteam.todolist.repository.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Repository
public class InMemoryUserDAOImpl implements UserDAO
{
    //TODO is returning deleted user makes any sense?
    //TODO ArrayList is not thread save, better to be replaced with ConcurrentHashMap
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public User addUser(User user)
    {
        if(user.getId() == null)
        {
            Long id = idGen.getAndIncrement();
            user.setId(id);
        }

        users.add(user);
        return user;
    }

    @Override
    public List<User> findAllUsers()
    {
        return users;
    }

    @Override
    public User findUserById(Long id)
    {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public User findUserByName(String name)
    {
        return users.stream().filter(u -> u.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public void updateUser(User user)
    {
        int index = IntStream.range(0, users.size())
                .filter(i -> users.get(i).getId().equals(user.getId()))
                .findFirst().orElse(-1);

        if(index != -1) users.set(index, user);
    }

    @Override
    public User deleteUser(Long id)
    {
        User user = users.stream()
                .filter(usr -> usr.getId().equals(id))
                .findFirst().orElse(null);
        users.remove(user);

        return user;
    }
}
