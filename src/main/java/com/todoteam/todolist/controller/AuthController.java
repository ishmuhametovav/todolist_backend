package com.todoteam.todolist.controller;

import com.todoteam.todolist.dto.AuthResponse;
import com.todoteam.todolist.dto.LoginRequest;
import com.todoteam.todolist.dto.RegistrationRequest;
import com.todoteam.todolist.model.User;
import com.todoteam.todolist.service.TokenService;
import com.todoteam.todolist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping
    ResponseEntity<Void> redirect()
    {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request)
    {
        try
        {
            User user = userService.addUser(request.name, request.plainPassword);
            String token = tokenService.createTokenForUser(user.getId());

            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName()));
        }
        catch (IllegalArgumentException ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        try
        {
            User user = userService.authenticate(request.name, request.plainPassword);
            String token = tokenService.createTokenForUser(user.getId());
            return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName()));
        }
        catch(IllegalArgumentException ex)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
