package com.example.back.responseModels;

import lombok.Data;

@Data
public class idMsg {
    int param1;

    public idMsg(int param1) {
        this.param1 = param1;
    }

    public idMsg(){
    }
}
