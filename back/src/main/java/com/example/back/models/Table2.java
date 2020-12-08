package com.example.back.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Table2 {

    int id;
    String name;
    public ArrayList<List2> lists;
    //public List2[] lists;

    public Table2(Table t) {
        this.id = t.getId();
        this.name = t.getName();
        lists = new ArrayList<List2>();
    }


}
