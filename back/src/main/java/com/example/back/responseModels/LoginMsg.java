package com.example.back.responseModels;


import lombok.Getter;

@Getter
public class LoginMsg {
    String login;
    String password;

    public LoginMsg(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
