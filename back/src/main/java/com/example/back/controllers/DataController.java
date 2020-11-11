package com.example.back.controllers;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;
import com.example.back.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class DataController {

    //TODO -- change repository to service
    UserRepository userRepository;
    //http://localhost:8080/user?id=1


    private UserServiceImpl service;

    public DataController(UserServiceImpl service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }


        @GetMapping("/user")
        public User getUser(@RequestParam int id){
            return userRepository.findById(id).get();
        }

        //http://localhost:8080/users/all
        @GetMapping("/users/all")
        public Iterable<User> getAllUsers(){
            return userRepository.findAll();
        }

        @PostMapping("/register")
        public ResponseEntity addUser(@RequestBody User user){
        //User user = new User(username,password);
        userRepository.save(user);
        //service.addUser(user);
        return new ResponseEntity("added",HttpStatus.OK);
    }

}