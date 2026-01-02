package com.todoteam.todolist.repository.impl;

import com.todoteam.todolist.model.User;
import com.todoteam.todolist.repository.UserDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Repository
public class InMemoryUserDAOImpl implements UserDAO
{
    //TODO is returning deleted user makes any sense?
    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(0);

    @Override
    public User addUser(User user)
    {
        if(user.getId() == null)
        {
            Long id = idGen.getAndIncrement();
            user.setId(id);
            users.put(id, user);
        }

        else
        {
            User prev = users.putIfAbsent(user.getId(), user);
            if(prev != null) return null;
        }
        return user;
    }

    @Override
    public List<User> findAllUsers()
    {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(Long id)
    {
        return users.get(id);
    }

    @Override
    public User findUserByName(String name)
    {
        return users.values().stream().filter(u -> u.getName().equals(name))
                .findFirst().orElse(null);
    }

    @Override
    public void updateUser(User user)
    {
        Long id = user.getId();
        users.replace(id, user);
    }

    @Override
    public User deleteUser(Long id)
    {
        return users.remove(id);
    }
}
