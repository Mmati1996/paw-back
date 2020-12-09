package com.example.back.models;

import lombok.Data;

import java.sql.Date;

@Data
public class Card2 {

    int id;
    String name;
    int list_id;
    public Date date;

    public Card2(Card c) {
        this.id = c.getId();
        this.name = c.getName();
        this.list_id = c.getList_id();
        this.date = c.getDate();
    }

}
