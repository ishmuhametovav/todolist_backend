package com.todoteam.todolist.service.impl;

import com.todoteam.todolist.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryTokenServiceImpl implements TokenService
{
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();
    @Override
    public String createTokenForUser(Long userId)
    {
        Objects.requireNonNull(userId, "userId must not be null");
        String token = UUID.randomUUID().toString();
        tokens.put(token, userId);

        return token;
    }

    @Override
    public Long getUserIdByToken(String token)
    {
        Objects.requireNonNull(token, "token must not be null");
        return tokens.get(token);
    }

    @Override
    public void revokeToken(String token)
    {
        Objects.requireNonNull(token, "token must not be null");
        tokens.remove(token);
    }

    @Override
    public void revokeAllForUser(Long userId)
    {
        Objects.requireNonNull(userId, "userId must not be null");
        tokens.entrySet().removeIf(e -> userId.equals(e.getValue()));
    }
}
