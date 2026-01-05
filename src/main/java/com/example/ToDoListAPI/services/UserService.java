package com.example.ToDoListAPI.services;

import com.example.ToDoListAPI.exceptions.BadRequestException;
import com.example.ToDoListAPI.exceptions.UnauthorizedException;
import com.example.ToDoListAPI.model.User;
import com.example.ToDoListAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new BadRequestException("Email already in use");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        return generateToken(user);
    }

    public String loginUser(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if(!bCryptPasswordEncoder.matches(password, user.getPassword()) ){
            throw new UnauthorizedException("Invalid email or password");
        }

        return generateToken(user);
    }

    private String generateToken(User user){
        // For simplicity, we're returning a dummy token. In a real application, use JWT or similar.
        return "fake-jwt-token-" + user.getId();
    }
}
