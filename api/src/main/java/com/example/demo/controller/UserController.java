package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;
import com.example.demo.entity.*;

import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Injection de dépendance
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<User> getAllUser() {
        return userRepository.findAll();

    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody User User) {
        return (User) userRepository.save(User);
    
    }

    @PutMapping("/{id}")
    public  User updateUser(@Valid @RequestBody User newUser) throws Throwable {
        User User = this.fetchUser(newUser.getId());
        User.setName(newUser.getName());
        return User;
        
    }
    
    public User fetchUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        return user;
    }
    

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long UserId) throws Throwable {
        return this.fetchUser(UserId);

    }
}