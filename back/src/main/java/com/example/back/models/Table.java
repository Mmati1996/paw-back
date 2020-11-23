package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
@javax.persistence.Table(name="tables")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;


    public Table(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Table(ArrayList<User> users) {
    }
}
