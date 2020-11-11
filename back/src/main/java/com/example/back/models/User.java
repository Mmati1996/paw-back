package com.example.back.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="users")
public class User {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;

    // == constructors ==
    public User(){
    }

    public User(int id, String login, String password){
        this.id = id;
        this.login = login;
        this.password= password;
    }

    public User(String login, String password){
        this.login = login;
        this.password= password;
    }

}
