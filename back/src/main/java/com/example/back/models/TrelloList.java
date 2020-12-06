package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="listy")
public class TrelloList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int table_id;

    // == constructors ==

    public TrelloList(int id, String name, int table_id) {
        this.id = id;
        this.name = name;
        this.table_id = table_id;
    }

    public TrelloList(String name, int table_id) {
        this.name = name;
        this.table_id = table_id;
    }

    public TrelloList() {
    }
}
