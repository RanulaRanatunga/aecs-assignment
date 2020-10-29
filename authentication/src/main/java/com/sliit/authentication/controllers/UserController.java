package com.sliit.authentication.controllers;

import com.sliit.authentication.entites.User;
import com.sliit.authentication.entites.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("create")
    public boolean create(@RequestBody User user) {
        return userRepository.createUser(user);
    }

    @PostMapping("authenticate")
    public boolean authenticate(@RequestBody User user) {
        return userRepository.authenticateUser(user);
    }

    @GetMapping("getAll")
    public List<User> getAll() {
        return userRepository.getAllUsers();
    }
}
