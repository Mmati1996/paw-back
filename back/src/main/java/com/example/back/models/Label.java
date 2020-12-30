package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="etykiety")
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    // == constructors ==
    public Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }


}
