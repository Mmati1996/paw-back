
package com.example.back.services;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;

public class UserServiceImpl implements UserService{

     UserRepository userRepository;



    public UserServiceImpl(UserService userService){
        this.userRepository = userRepository;
    }


    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public String getLogin(int id) {
        return userRepository.findById(id).get().getLogin();
    }

    @Override
    public String getPassword(int id) {
        return userRepository.findById(id).get().getPassword();
    }
}
