package com.example.back.controllers;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;
import com.example.back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private static UserService userService;

    public DataController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    UserRepository repository;


    //http://localhost:8080/user?id=1
    @GetMapping("/user")
    public static User getUser(@RequestParam int id){
        return userService.getUserById(id);
    }

    @GetMapping("/users/all")
    public Iterable<User> getAllUsers(){
        return repository.findAll();
    }

}