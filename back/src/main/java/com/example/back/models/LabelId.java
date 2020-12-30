package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
@javax.persistence.Table(name="etykietyids")
public class LabelId {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int labelId;
    int cardId;

    // == constructors ==
    public LabelId(int id, int labelId, int cardId) {
        this.id = id;
        this.labelId = labelId;
        this.cardId = cardId;
    }

    public LabelId(int labelId, int cardId) {
        this.labelId = labelId;
        this.cardId = cardId;
    }

    public LabelId() {
    }
}
