package com.example.back.services;

import com.example.back.models.Card;
import com.example.back.repositories.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService{

    private CardRepository cardRepository;

    public CardServiceImpl(){
    }

    public CardServiceImpl(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    @Override
    public Card getCardById(int id) {
        return cardRepository.findById(id).get();
    }

    @Override
    public String getName(int id) {
        return cardRepository.findById(id).get().getName();
    }
}
