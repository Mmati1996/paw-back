package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="karty")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int list_id;
    int year;
    int month;
    int day;


    // == constructors ==
    public Card(int id, String name, int list_id) {
        this.id = id;
        this.name = name;
        this.list_id = list_id;
        year=0;
        month=0;
        day=0;
    }

    public Card(String name, int list_id) {
        this.name = name;
        this.list_id = list_id;
        year=0;
        month=0;
        day=0;
    }

    public Card() {
        year=0;
        month=0;
        day=0;
    }
}
