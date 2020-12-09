package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
@javax.persistence.Table(name="karty")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int list_id;
    //Timestamp date;
    public Date date;


    // == constructors ==
    public Card(int id, String name, int list_id) {
        this.id = id;
        this.name = name;
        this.list_id = list_id;
        date = null;
    }

    public Card(String name, int list_id) {
        this.name = name;
        this.list_id = list_id;
        date = null;
    }

    public Card() {
        date = null;
    }
}
