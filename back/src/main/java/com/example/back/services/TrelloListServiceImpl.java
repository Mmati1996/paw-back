package com.example.back.services;

import com.example.back.models.TrelloList;
import com.example.back.repositories.TrelloListRepository;
import org.springframework.stereotype.Service;

@Service
public class TrelloListServiceImpl implements TrelloListService{

    public TrelloListRepository trelloListRepository;

    public TrelloListServiceImpl(TrelloListRepository trelloListRepository) {
        this.trelloListRepository = trelloListRepository;
    }

    public TrelloListServiceImpl(){

    }

    @Override
    public TrelloList getCardById(int id) {
        return null;
    }

    @Override
    public String getName(int id) {
        return null;
    }
}
