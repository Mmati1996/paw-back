package com.example.back.services;

import com.example.back.models.Table;

public interface TableService {

    Table getTableById(int id);
    public String getName(int id);
    void addTable(Table table);
}
