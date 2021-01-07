package com.example.back.models;

import lombok.Data;

@Data
public class TaskBuilder {
    String title;
    int isDone;

    public Task buildTask(int cardId){
            return new Task(this.title, this.isDone, cardId);


    }
}
