package com.example.back.services;

import com.example.back.models.TrelloList;

public interface TrelloListService {

    TrelloList getCardById(int id);
    public String getName(int id);

}
