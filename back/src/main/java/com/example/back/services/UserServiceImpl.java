
package com.example.back.services;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

     private UserRepository userRepository;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserRepository userRepository){
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

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }



}
