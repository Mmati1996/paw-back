package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="tablice")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    // == constructors ==
    public Table(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Table(){
    }

    public Table(String name) {
        this.name = name;
    }
}
