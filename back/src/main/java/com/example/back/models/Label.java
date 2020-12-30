package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    // == constructors ==
    public Label(int id, String name, ArrayList<Integer> cards) {
        this.id = id;
        this.name = name;
    }

    public Label(String name, ArrayList<Integer> cards) {
        this.name = name;
    }

    public Label() {
    }


}
