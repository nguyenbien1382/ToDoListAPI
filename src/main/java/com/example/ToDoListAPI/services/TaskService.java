package com.example.ToDoListAPI.services;

import com.example.ToDoListAPI.exceptions.ResourceNotFoundException;
import com.example.ToDoListAPI.model.Task;
import com.example.ToDoListAPI.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task,Long userID) {
        task.setUserId(userID);
        taskRepository.save(task);
        return task;
    }

    public List<Task> getAllTasks(Long userId, int page, int limit){
        int offset = (page - 1) * limit;
        return taskRepository.findAllByUserId(userId, limit, offset);
    }

    public Task updateTask (Long id, Task taskUpdate, Long userId){
        taskUpdate.setId(id);
        taskUpdate.setUserId(userId);

        int rowsAffected = taskRepository.update(taskUpdate);

        if(rowsAffected == 0){
            throw new ResourceNotFoundException("Task not found or you do not have permission");
        }

        return taskUpdate;
    }

    public void deleteTask(Long id, Long userId){
        int rowsAffected = taskRepository.deleteById(id, userId);

        if(rowsAffected == 0){
            throw new ResourceNotFoundException("Task not found or you do not have permission");
        }
    }
}
