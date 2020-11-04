package com.example.back.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
/*
    private static UserService userService;

    public DataController(UserService userService) {
        this.userService = userService;
    }

*/

    //http://localhost:8080/user?id=1
    @GetMapping("/user")
    public static String getUser(@RequestParam int id){
        //return userService.getLogin(id);
        return "test string";
    }

}