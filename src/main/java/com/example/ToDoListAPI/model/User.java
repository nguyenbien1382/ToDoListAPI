package com.example.ToDoListAPI.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
}
