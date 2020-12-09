package com.example.back.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class List2 {

    int id;
    String name;
    int table_id;
    public ArrayList<Card2> cards;
    //public Card[] cards;

    public List2(TrelloList tl) {
        this.id = tl.getId();
        this.name = tl.getName();
        cards = new ArrayList<Card2>();
    }
}
