package com.example.back.models;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;

@Data
public class Card2 {

    int id;
    String name;
    int list_id;
    public Date date;
    public ArrayList<Task2> tasks;

    public Card2(Card c) {
        this.id = c.getId();
        this.name = c.getName();
        this.list_id = c.getList_id();
        this.date = c.getDate();
        this.tasks = new ArrayList<Task2>();
    }

}
