package com.example.back.responseModels;


import lombok.Data;

@Data
public class Message {
    String param1;
    String param2;

    public Message(String par1, String par2) {
        this.param1 = par1;
        this.param2 = par2;
    }

    public Message(){

    }
}
