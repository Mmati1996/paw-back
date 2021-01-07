package com.example.back.models;

import lombok.Data;

@Data
public class TaskBuilder {
    String title;
    String isDone;

    public Task buildTask(int cardId){
        if ((this.isDone).equals("false")){
            return new Task(this.title,0, cardId);

        }else{
            return new Task(this.title,1, cardId);
        }

    }
}
