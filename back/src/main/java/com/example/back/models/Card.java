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
    int table_id;

    // == constructors ==


    public Card(int id, String name, int table_id) {
        this.id = id;
        this.name = name;
        this.table_id = table_id;
    }

    public Card(String name, int table_id) {
        this.name = name;
        this.table_id = table_id;
    }

    public Card() {
    }
}
