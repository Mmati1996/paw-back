package com.example.back.responseModels;

import lombok.Data;

@Data
public class ShortMessage {
    String param1;

    public ShortMessage(String param1) {
        this.param1 = param1;
    }

    public ShortMessage(){
    }
}
