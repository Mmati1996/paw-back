package com.example.back.responseModels;

import lombok.Data;

@Data
public class LoginResponse {
    boolean ok;
    Token data;
    String error;

    public LoginResponse(boolean ok, Token data, String error) {
        this.ok = ok;
        this.data = data;
        this.error = error;
    }
}
