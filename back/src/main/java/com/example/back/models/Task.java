package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@javax.persistence.Table(name="taski")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    boolean isDone;
    int cardId;

    // == constructors ==
    public Task(int id, String title, boolean isDone, int cardId) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
        this.cardId = cardId;
    }

    public Task(String title, boolean isDone, int cardId) {
        this.title = title;
        this.isDone = isDone;
        this.cardId = cardId;
    }

    public Task() {
    }

}
