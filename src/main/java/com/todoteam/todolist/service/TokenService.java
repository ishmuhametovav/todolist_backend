package com.todoteam.todolist.service;

public interface TokenService
{
    String createTokenForUser(Long userId);

    Long getUserIdByToken(String token);

    void revokeToken(String token);

    void revokeAllForUser(Long userId);
}
