package com.example.back.services;

import com.example.back.models.Card;

public interface CardService {

    Card getCardById(int id);
    public String getName(int id);
}
