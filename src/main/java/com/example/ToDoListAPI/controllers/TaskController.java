package com.example.ToDoListAPI.controllers;

import com.example.ToDoListAPI.exceptions.UnauthorizedException;
import com.example.ToDoListAPI.model.Task;
import com.example.ToDoListAPI.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private Long getUserIdFromToken(String token){
        if (token == null || !token.startsWith("Bearer ")){
            throw new UnauthorizedException("Missing or invalid token");
        }

        try{
            String idPart =  token.replace("Bearer fake-jwt-token-", "");
            return Long.parseLong(idPart);

        } catch(NumberFormatException e){
            throw new UnauthorizedException("Invalid token format");

        }
    }

    @PostMapping
    public Task create(@RequestHeader("Authorization") String token, @RequestBody Task task){
        Long userId = getUserIdFromToken(token);
        return taskService.createTask(task, userId);
    }

    @GetMapping
    public Map<String,Object> getAll(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        Long userId = getUserIdFromToken(token);
        List<Task> tasks = taskService.getAllTasks(userId, page, limit);

        Map<String, Object> response = new HashMap<>();
        response.put("data", tasks);
        response.put("page",page);
        response.put("limit", limit);
        response.put("total", tasks.size());
        return response;
    }

    @PutMapping("/{id}")
    public Task update(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Task task
    ){
        Long userId = getUserIdFromToken(token);
        return taskService.updateTask(id, task, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ){
        Long userId = getUserIdFromToken(token);
        taskService.deleteTask(id, userId);
    }
    
}
