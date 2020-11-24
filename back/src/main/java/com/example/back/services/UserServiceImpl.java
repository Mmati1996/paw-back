
package com.example.back.services;

import com.example.back.models.User;
import com.example.back.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public User getUserByLogin(String login){
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        for (User user : users){
            if (  (user.getLogin()).equals(login)  ){
                return user;
            }
        }
        return null;
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
