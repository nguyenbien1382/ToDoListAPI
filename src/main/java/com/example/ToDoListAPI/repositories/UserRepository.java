package com.example.ToDoListAPI.repositories;

import com.example.ToDoListAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(User user) {
        return jdbcTemplate.update(
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try{
            User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return u;
            }, email);
            return Optional.ofNullable(user);
        } catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
 }

