package com.example.back.responseModels;

import lombok.Data;

@Data
public class Response {
    boolean ok;
    String data;
    String error;

    public Response(boolean ok, String data, String error) {
        this.ok = ok;
        this.data = data;
        this.error = error;
    }
}
