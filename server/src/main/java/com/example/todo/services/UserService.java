package com.example.todo.services;

import com.example.todo.entities.User;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

}
