package com.example.ToDoListAPI.repositories;

import com.example.ToDoListAPI.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Task task){
        return jdbcTemplate.update(
                "INSERT INTO tasks (title,description,user_id) VALUES (?,?,?) ",
                task.getTitle(),
                task.getDescription(),
                task.getUserId()
        );
    }

    public List<Task> findAllByUserId(Long userId, int limit, int offset){
        String sql = "SELECT * FROM tasks WHERE user_id = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,(rs,rowNum) ->
        {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setUserId(rs.getLong("user_id"));
            return task;
        },userId,limit,offset );
    }

    public int update(Task task ){
        return jdbcTemplate.update(
                "UPDATE  tasks SET title = ?, description = ? WHERE id = ? AND user_id = ?",
                task.getTitle(),task.getDescription(),task.getId(),task.getUserId()
        );
    }

    public int deleteById(Long id, Long userId){
        return jdbcTemplate.update(
                "DELETE FROM tasks WHERE id = ? AND user_id = ?",
                id,userId
        );
    }
}
