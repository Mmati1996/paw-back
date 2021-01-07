package com.example.back.models;

import lombok.Data;

@Data
public class TaskBuilder {
    String title;
    boolean isDone;

    public Task buildTask(int cardId){
        if (this.isDone){
            return new Task(this.title,1, cardId);
        }else{
            return new Task(this.title,0, cardId);
        }



    }
}
