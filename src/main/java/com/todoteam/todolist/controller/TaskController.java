package com.todoteam.todolist.controller;

import com.todoteam.todolist.dto.CreateTaskRequest;
import com.todoteam.todolist.dto.TaskResponse;
import com.todoteam.todolist.dto.UpdateTaskRequest;
import com.todoteam.todolist.mapper.TaskMapper;
import com.todoteam.todolist.model.Task;
import com.todoteam.todolist.model.User;
import com.todoteam.todolist.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/tasks")
public class TaskController
{
    private final TaskService taskService;

    @PostMapping
    ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest request,
                                 @AuthenticationPrincipal User user)
    {
        try
        {
            System.out.println("User: " + user);
            System.out.println("We are here!");

            Task t = TaskMapper.fromCreateRequestToTask(request, user.getId());

            taskService.addTask(t);

            return ResponseEntity.ok(TaskMapper.toResponse(t));
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserTasks(@AuthenticationPrincipal User user)
    {
        List<Task> tasks = taskService.findTasksByUserId(user.getId());
        return ResponseEntity.ok(TaskMapper.toResponseList(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserTask(@PathVariable Long id
            , @AuthenticationPrincipal User user)
    {
        try
        {
            Task t = taskService.findTaskById(id);
            return ResponseEntity.ok(TaskMapper.toResponse(t));
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserTask(@PathVariable Long id
            , @Valid @RequestBody UpdateTaskRequest request, @AuthenticationPrincipal User user)
    {
        try
        {//TODO is it correct that id fields are filled within a controller method?
            Task t = TaskMapper.fromUpdateRequestToTask(request, user.getId());
            t.setId(id);
            taskService.updateTask(t);

            return ResponseEntity.ok(TaskMapper.toResponse(t));

        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserTask(@PathVariable Long id, @AuthenticationPrincipal User user)
    {
        try
        {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
