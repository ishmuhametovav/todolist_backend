package com.todoteam.todolist.service.impl;

import com.todoteam.todolist.model.User;
import com.todoteam.todolist.repository.impl.InMemoryUserDAOImpl;
import com.todoteam.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InMemoryUserServiceImpl implements UserService
{
    private final InMemoryUserDAOImpl userDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User authenticate(String name, String plainPassword)
    {
        User user = userDAO.findUserByName(name);
        if(user == null)
        { 
            throw new IllegalArgumentException("No such user");
        }

        if(!passwordEncoder.matches(plainPassword, user.getHashPassword()))
        {
            throw new IllegalArgumentException("password is not correct");
        }

        return user;
    }

    @Override
    public User addUser(String name, String plainPassword)
    {
        String hashedPassword = passwordEncoder.encode(plainPassword);

        User user = User.builder().name(name).hashPassword(hashedPassword).build();

        return userDAO.addUser(user);
    }

    @Override
    public List<User> findAllUsers()
    {
        return userDAO.findAllUsers();
    }

    @Override
    public User findUserById(Long id)
    {
        return userDAO.findUserById(id);
    }

    @Override
    public void updateUser(User user)
    {
        userDAO.updateUser(user);
    }

    @Override
    public User deleteUser(Long id)
    {
        return userDAO.deleteUser(id);
    }
}
