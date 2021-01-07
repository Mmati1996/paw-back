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
    int is_done;
    int card_id;

    // == constructors ==
    public Task(int id, String title, int is_done, int card_id) {
        this.id = id;
        this.title = title;
        this.is_done = is_done;
        this.card_id = card_id;
    }

    public Task(String title, int is_done, int card_id) {
        this.title = title;
        this.is_done = is_done;
        this.card_id = card_id;
    }

    public Task() {
    }

    public Task(Task2 task2, int id) {
        this.id = task2.id;
        this.title = task2.title;
        if (task2.isDone){
            this.is_done = 1;
        }else{
            this.is_done = 0;
        }
        this.card_id = id;
    }
}