package com.example.back.models;


public class Task2 {
    public int id;
    public String title;
    public boolean isDone;

    public Task2 (Task t){
        this.id = t.getId();
        this.title = t.getTitle();
        if (t.getIs_done() == 0){
            this.isDone = false;
        }else{
            this.isDone = true;
        }

    }

}
