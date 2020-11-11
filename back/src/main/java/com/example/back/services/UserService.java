
package com.example.back.services;

import com.example.back.models.User;

public interface UserService {

    User getUserById(int id);
    public String getLogin(int id);
    public String getPassword(int id);
    void addUser(User user);
}
