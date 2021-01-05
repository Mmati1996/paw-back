package com.example.back.models;

import lombok.Data;


@Data
public class Task2 {
    int id;
    String title;
    boolean isDone;
    int cardId;

    public Task2 (Task t){
        this.id = t.getId();
        this.title = t.getTitle();
        this.isDone = t.isDone;
        this.cardId = t.cardId;
    }

}