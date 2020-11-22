package com.example.back.controllers;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;
import com.example.back.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @PostMapping("/login")
    public ResponseEntity login (@RequestParam String login){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for ( User user : users  ){
            if((user.getLogin()).equals(login)){
                //generate token here
                return new ResponseEntity("token",HttpStatus.OK);
            }
        }
        return new ResponseEntity("no user found",HttpStatus.NOT_FOUND);
    }

}