package com.example.back.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
@javax.persistence.Table(name="ids")
public class Id {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int user_id;
    int table_id;


    // == constructors ==
    public Id(){
    }

    public Id(int user_id, int table_id) {
        this.user_id = user_id;
        this.table_id = table_id;
    }

    public Id(int id, int user_id, int table_id) {
        this.id = id;
        this.user_id = user_id;
        this.table_id = table_id;
    }
}
